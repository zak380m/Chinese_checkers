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

    private interface CommandHandler {
        void handle(Command command);
    }

    private void initializeCommands() {
        commandHandlers = new HashMap<>();

        commandHandlers.put("move", this::handleMoveCommand);
        commandHandlers.put("display", this::handleDisplayCommand);
        commandHandlers.put("pass", this::handlePassCommand);
        commandHandlers.put("quit", this::handleQuitCommand);
        commandHandlers.put("message", this::handleMessageCommand);
    }

    @Override
    public void run() {
        try {
            while (isConnected) {
                while (!isReady){
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

    private void handlePassCommand(Command command) {
        if (gamemode.getTurn() == playerNumber) {
            room.processPass();
            System.out.println("Player " + playerNumber + " pass.");
        } else {
            sendErrorMessage("Not your turn.");
        }
    }

    private void handleDisplayCommand(Command command) {
        sendJsonReply(room.currentGameState(playerNumber));
    }

    private void handleQuitCommand(Command command) {
        isConnected = false;
    }

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

    public void sendErrorMessage(String message) {
        try {
            out.writeObject(gson.toJson(new ErrorMessage(message)));
            out.flush();
        } catch (IOException e) {
            System.out.println("Failed to send error message to a player - connection lost.");
        }
    }

    public void sendJsonReply(String jsonReply) {
        try {
            out.writeObject(jsonReply);
            out.flush();
        } catch (IOException e) {
            System.out.println("Failed to send json reply to a player - connection lost.");
        }
    }

    public void handleDisconnection() {
        room.removePlayer(playerNumber);
        isConnected = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerInRoom(int playerNumber, GameRoom room, Gamemode gamemode) {
        this.playerNumber = playerNumber;
        this.room = room;
        this.gamemode = gamemode;
    }

    public void sendSetUp(SetUp setUp) {
        System.out.println("Sending setup to player");
        try {
            out.writeObject(gson.toJson(setUp));
            out.flush();
        } catch (IOException e) {
            sendErrorMessage("Disconection?");
        }
    }

    public SetUp getSetUp() {
        System.out.println("Getting setup from player");
        try {
            String jsonString = (String) in.readObject();
            return gson.fromJson(jsonString, SetUp.class);
        } catch (IOException | ClassNotFoundException e) {
            sendErrorMessage("Wrong setup.");
        }
        return null;
    }

    private void gettingReady() {
        try {
            SetUp setUp = new SetUp(gameBuilder.getGameList(), gameBuilder.getBoardList());
            sendSetUp(setUp);
            try {
                System.out.println("Waiting for setup.");
                SetUp info = getSetUp();
                if (info.isCreate()) {
                    if(createGameRoom(info.getGamemode(), info.getPlayerCount(), info.getBotCount(), info.getPassword())) isReady = true;
                } else if (!info.isCreate()) {
                    GameRoom gameRoom = server.joinGameRoom(info.getPassword());
                    if(gameRoom != null) isReady = gameRoom.addPlayer(this);
                }
            } catch (Exception e) {
                sendErrorMessage("Try again, wrong setup.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createGameRoom(String gamemodeName, int playerCount, int botCount, String password) {
        System.out.println("Setting up gamemode: " + gamemodeName);
        gameBuilder.setGameName(gamemodeName);
        GamemodeBuilder gamemodeBuilder = gameBuilder.getGamemodeBuilder();
        BoardBuilder boardBuilder = gameBuilder.getBoardBuilder();
        boardBuilder.buildBoard(playerCount);
        if(boardBuilder.getBoard() == null) {
            System.out.println("Board setup failed.");
            return false;
        }
        Board board = boardBuilder.getBoard();
        gamemodeBuilder.buildGamemode(playerCount, board);
        if(gamemodeBuilder.getGamemode() == null) {
            System.out.println("Gamemode setup failed.");
            return false;
        }
        Gamemode gamemode = gamemodeBuilder.getGamemode();
        System.out.println("Gamemode set up is done.");
        GameRoom gameRoom = server.createGameRoom(gamemode, board, password, playerCount, botCount);
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
