package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Messages.ErrorMessage;
import zak380mGazyli.Messages.Message;

public class PlayerHandler implements Runnable {
    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Gamemode gamemode;
    private int playerNumber;
    private Boolean isConnected;
    private Gson gson;
    private Map<String, CommandHandler> commandHandlers;

    public PlayerHandler(Socket socket, Server server, int playerNumber) {
        this.socket = socket;
        this.server = server;
        this.gamemode = server.getGamemode() != null ? server.getGamemode() : null;
        this.playerNumber = playerNumber;
        this.isConnected = true;
        this.gson = new Gson();
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
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            if (gamemode == null) {
                while (isConnected && gamemode == null) {
                    setUpGamemode();
                }
            }

            while (isConnected) {
                String jsonString = (String) in.readObject();
                Command command = gson.fromJson(jsonString, Command.class);
                System.out.println("Player " + playerNumber + " sent command: " + command.getName());

                CommandHandler commandHandler = commandHandlers.get(command.getName());
                if (commandHandler != null) {
                    commandHandler.handle(command);
                } else {
                    sendErrorMessage("Unknown command.");
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
                server.processMove(startX, startY, endX, endY);
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
            server.processPass();
            System.out.println("Player " + playerNumber + " pass.");
        } else {
            sendErrorMessage("Not your turn.");
        }
    }

    private void handleDisplayCommand(Command command) {
        sendJsonReply(server.currentGameState(playerNumber));
    }

    private void handleQuitCommand(Command command) {
        handleDisconnection();
    }

    private void handleMessageCommand(Command command) {
        if (command.getArgs().length == 1 && command.getTextArg() != null) {
            if (command.getArgs()[0] == -1) {
                server.broadcastToAllExceptOne(gson.toJson(new Message(command.getTextArg() + " FROM PLAYER: " + playerNumber)), playerNumber);
            } else {
                int recipient = command.getArgs()[0];
                if (recipient >= 0 && recipient < server.getNumberOfPlayers()) {
                    server.sendToAPlayer(gson.toJson(new Message(command.getTextArg() + " FROM PLAYER: " + playerNumber)), recipient);
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
            e.printStackTrace();
        }
    }

    public void sendJsonReply(String jsonReply) {
        try {
            out.writeObject(jsonReply);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDisconnection() {
        isConnected = false;
        server.removePlayer(this);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpGamemode() {
        try {
            out.writeObject(gson.toJson(new Message("Send setUpGameMode.")));
            out.flush();
            String jsonString = (String) in.readObject();
            Command command = gson.fromJson(jsonString, Command.class);
            System.out.println("Player " + playerNumber + " sent command: " + command.getName());

            if ("setUpGamemode".equals(command.getName()) && command.getArgs().length == 2) {
                if (!server.setUpGamemode(command.getTextArg() , command.getArgs()[0], command.getArgs()[1])) {
                    sendErrorMessage("Try again, invalid setup.");
                }
                gamemode = server.getGamemode();
            } else {
                sendErrorMessage("Try again, invalid setup.");
            }
        } catch (SocketException e) {
            isConnected = false;
        } catch (IOException | ClassNotFoundException e) {
            sendErrorMessage(e.getMessage());
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
