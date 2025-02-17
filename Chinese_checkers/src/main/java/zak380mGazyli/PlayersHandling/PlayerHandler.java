package zak380mGazyli.PlayersHandling;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import zak380mGazyli.Server;
import zak380mGazyli.Boards.Board;
import zak380mGazyli.Builders.GameBuilder;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Messages.ErrorMessage;
import zak380mGazyli.Messages.Message;
import zak380mGazyli.Misc.SetUp;

/**
 * The PlayerHandler class handles the communication between the server and a player.
 * It processes commands sent by the player and manages the player's state in the game.
 */
public class PlayerHandler implements Runnable {
    private Server server;
    private Socket socket;
    private GameRoom room;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Gamemode gamemode;
    private int playerNumber;
    private Boolean isConnected;
    private Gson gson;
    private Map<String, CommandHandler> commandHandlers;
    private volatile boolean isReady;
    private GameBuilder gameBuilder;

    /**
     * Constructs a PlayerHandler instance.
     *
     * @param socket The socket for communication with the player.
     * @param server The server managing the game.
     */
    public PlayerHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.isConnected = true;
        this.isReady = false;
        this.gson = new Gson();
        this.gameBuilder = new GameBuilder();
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Failed to create input/output streams for player.");
        }
        initializeCommands();
    }

    /**
     * Interface for handling commands.
     */
    private interface CommandHandler {
        void handle(Command command);
    }

    /**
     * Initializes the command handlers.
     */
    private void initializeCommands() {
        commandHandlers = new HashMap<>();

        commandHandlers.put("move", this::handleMoveCommand);
        commandHandlers.put("display", this::handleDisplayCommand);
        commandHandlers.put("pass", this::handlePassCommand);
        commandHandlers.put("quit", this::handleQuitCommand);
        commandHandlers.put("message", this::handleMessageCommand);
    }

    /**
     * The main run method for the PlayerHandler thread.
     * It listens for commands from the player and processes them.
     */
    @Override
    public void run() {
        try {
            while (isConnected) {
                while (!isReady && isConnected) {
                    gettingReady();
                }
                String jsonString = (String) in.readObject();
                Command command = gson.fromJson(jsonString, Command.class);
                System.out.println("Player " + playerNumber + " sent command: " + command.getName());

                CommandHandler commandHandler = commandHandlers.get(command.getName());
                if (commandHandler != null) {
                    commandHandler.handle(command);
                } else {
                    sendErrorMessage("Unknown command.");
                }
                if(room.isGameFinished()) {
                    room.removePlayer(playerNumber);
                }
            }
            if(!isConnected) {
                System.out.println("Player " + playerNumber + " disconnected.");
                handleDisconnection();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Player " + playerNumber + " disconnected.");
            handleDisconnection();
        }
    }

    /**
     * Handles the move command from the player.
     *
     * @param command The move command.
     */
    private void handleMoveCommand(Command command) {
        if (command.getArgs().length == 4) {
            int startX = command.getArgs()[0];
            int startY = command.getArgs()[1];
            int endX = command.getArgs()[2];
            int endY = command.getArgs()[3];

            if (gamemode.getTurn() == playerNumber) {
                room.processMove(startX, startY, endX, endY);
                System.out.println("Player " + playerNumber + " moved from " +
                        startX + ", " + startY + " to " + endX + ", " + endY);
            } else {
                sendErrorMessage("Not your turn.");
            }
        } else {
            sendErrorMessage("Invalid number of arguments for move.");
        }
    }

    /**
     * Handles the pass command from the player.
     *
     * @param command The pass command.
     */
    private void handlePassCommand(Command command) {
        if (gamemode.getTurn() == playerNumber) {
            room.processPass();
            System.out.println("Player " + playerNumber + " pass.");
        } else {
            sendErrorMessage("Not your turn.");
        }
    }

    /**
     * Handles the display command from the player.
     *
     * @param command The display command.
     */
    private void handleDisplayCommand(Command command) {
        sendJsonReply(room.currentGameState(playerNumber));
    }

    /**
     * Handles the quit command from the player.
     *
     * @param command The quit command.
     */
    private void handleQuitCommand(Command command) {
        isConnected = false;
    }

    /**
     * Handles the message command from the player.
     *
     * @param command The message command.
     */
    private void handleMessageCommand(Command command) {
        if (command.getArgs().length == 1 && command.getTextArg() != null) {
            if (command.getArgs()[0] == -1) {
                room.broadcastToAllExceptOne(gson.toJson(new Message(command.getTextArg() + " FROM PLAYER: " + playerNumber)), playerNumber);
            } else {
                int recipient = command.getArgs()[0];
                if (recipient >= 0 && recipient < room.getNumberOfPlayers()) {
                    room.sendToAPlayer(gson.toJson(new Message(command.getTextArg() + " FROM PLAYER: " + playerNumber)), recipient);
                } else {
                    sendErrorMessage("Invalid player number.");
                }
            }
        } else {
            sendErrorMessage("Invalid number of arguments for message.");
        }
    }

    /**
     * Sends an error message to the player.
     *
     * @param message The error message to send.
     */
    public void sendErrorMessage(String message) {
        try {
            out.writeObject(gson.toJson(new ErrorMessage(message)));
            out.flush();
        } catch (IOException e) {
            System.out.println("Failed to send error message to a player - connection lost.");
            handleDisconnection();
        }
    }

    /**
     * Sends a JSON reply to the player.
     *
     * @param jsonReply The JSON reply to send.
     */
    public void sendJsonReply(String jsonReply) {
        try {
            out.writeObject(jsonReply);
            out.flush();
        } catch (IOException e) {
            System.out.println("Failed to send json reply to a player - connection lost.");
            handleDisconnection();
        }
    }

    /**
     * Handles the disconnection of the player.
     */
    public void handleDisconnection() {
        if(room != null) room.removePlayer(playerNumber);
        isConnected = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the player number.
     *
     * @return The player number.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Sets the player in the room.
     *
     * @param playerNumber The player number.
     * @param room The game room.
     * @param gamemode The game mode.
     */
    public void setPlayerInRoom(int playerNumber, GameRoom room, Gamemode gamemode) {
        this.playerNumber = playerNumber;
        this.room = room;
        this.gamemode = gamemode;
    }

    /**
     * Sends the setup configuration to the player.
     *
     * @param setUp The setup configuration.
     */
    public void sendSetUp(SetUp setUp) {
        System.out.println("Sending setup to player");
        try {
            out.writeObject(gson.toJson(setUp));
            out.flush();
        } catch (IOException e) {
            sendErrorMessage("Disconection?");
        }
    }

    /**
     * Gets the setup configuration from the player.
     *
     * @return The setup configuration.
     */
    public SetUp getSetUp() {
        System.out.println("Getting setup from player");
        try {
            String jsonString = (String) in.readObject();
            if(jsonString.contains("quit")) {
                isConnected = false;
                handleDisconnection();
                return null;
            }
            return gson.fromJson(jsonString, SetUp.class);
        } catch (IOException | ClassNotFoundException e) {
            sendErrorMessage("Wrong setup.");
        }
        return null;
    }

    /**
     * Handles the player getting ready for the game.
     */
    private void gettingReady() {
        try {
            SetUp setUp = new SetUp(gameBuilder.getGamemodesList(), gameBuilder.getBoardsList());
            sendSetUp(setUp);
            try {
                System.out.println("Waiting for setup.");
                SetUp info = getSetUp();
                if (info.isCreate()) {
                    if(createGameRoom(info.getGamemode(), info.getBoard(), info.getPlayerCount(), info.getBotCount(), info.getPassword())) isReady = true;
                } else if(info.isLoad()) {
                    GameRoom gameRoom = server.loadGameRoom(info.getGameId(), info.getPassword());
                    if(gameRoom != null) isReady = gameRoom.addPlayer(this);
                } else if (!info.isCreate()) {
                    GameRoom gameRoom = server.joinGameRoom(info.getPassword());
                    if(gameRoom != null) isReady = gameRoom.addPlayer(this);
                }
            } catch (Exception e) {
                sendErrorMessage("Try again, wrong setup.");
            }
        } catch (Exception e) {
            handleDisconnection();
        }
    }

    /**
     * Creates a game room.
     *
     * @param gamemodeName The name of the game mode.
     * @param boardName The name of the board.
     * @param playerCount The number of players.
     * @param botCount The number of bots.
     * @param password The password for the game room.
     * @return true if the game room was created successfully, false otherwise.
     */
    public boolean createGameRoom(String gamemodeName, String boardName, int playerCount, int botCount, String password) {
        System.out.println("Setting up gamemode: " + gamemodeName);
        gameBuilder.setGamemodeName(gamemodeName);
        gameBuilder.setBoardName(boardName);
        GamemodeBuilder gamemodeBuilder = gameBuilder.getGamemodeBuilder();
        BoardBuilder boardBuilder = gameBuilder.getBoardBuilder();
        boardBuilder.buildBoard(playerCount + botCount);
        if(boardBuilder.getBoard() == null) {
            System.out.println("Board setup failed.");
            return false;
        }
        Board board = boardBuilder.getBoard();
        gamemodeBuilder.buildGamemode(playerCount + botCount, board);
        if(gamemodeBuilder.getGamemode() == null) {
            System.out.println("Gamemode setup failed.");
            return false;
        }
        Gamemode gamemode = gamemodeBuilder.getGamemode();
        System.out.println("Gamemode set up is done.");
        GameRoom gameRoom = server.createGameRoom(gamemode, board, password, playerCount, botCount, null);
        if(gameRoom != null) {
            System.out.println("Game room created.");
            if(gameRoom.addPlayer(this)) return true;
        } else {
            System.out.println("Game room creation failed.");
            return false;
        }
        return false;
    }
}
