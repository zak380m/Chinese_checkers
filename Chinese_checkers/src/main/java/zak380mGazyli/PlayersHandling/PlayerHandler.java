package zak380mGazyli.PlayersHandling;

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
    private GameRoom room;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Gamemode gamemode;
    private int playerNumber;
    private Boolean isConnected;
    private Gson gson;
    private Map<String, CommandHandler> commandHandlers;

    public PlayerHandler(Socket socket, ObjectOutputStream out, ObjectInputStream in, GameRoom room, int playerNumber) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.room = room;
        this.gamemode = room.getGamemode();
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
        handleDisconnection();
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
        room.removePlayer(this);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
