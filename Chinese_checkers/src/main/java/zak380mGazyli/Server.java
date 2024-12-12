package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import zak380mGazyli.Boards.*;
import zak380mGazyli.Builders.GamemodeBuilders.*;
import zak380mGazyli.Builders.GameBuilder;
import zak380mGazyli.Builders.BoardBuilders.*;
import zak380mGazyli.Gamemodes.*;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Messages.ErrorMessage;
import zak380mGazyli.Messages.Message;

public class Server {
    private List<PlayerHandler> players = new ArrayList<>();
    private Gamemode gamemode;  
    private Board board;
    private int numberOfPlayers;
    private volatile boolean settingUp;

    public static void main(String[] args) throws IOException {
        new Server().startServer();
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);

        System.out.println("Server started on port 5555.");

        settingUp = true;

        Socket playerSocket = serverSocket.accept();
        System.out.println("Player zero connected.");
        PlayerHandler playerHandler = new PlayerHandler(playerSocket, this, players.size());
        players.add(playerHandler);
        new Thread(playerHandler).start();
        while(settingUp){
        }
        System.out.println("Gamemode is set up.");

        while (numberOfPlayers > players.size()) {
            Socket playerSocket2 = serverSocket.accept();
            System.out.println("New player connected.");
            PlayerHandler playerHandler2 = new PlayerHandler(playerSocket2, this, players.size());
            players.add(playerHandler2);
            new Thread(playerHandler2).start();
        }
    }

    public synchronized void processMove(int startX, int startY, int endX, int endY) {
        if (gamemode.validateMove(startX, startY, endX, endY, board)) {
            gamemode.processMove(startX, startY, endX, endY, board);
            broadcastCurrentGameState();
        }
    }

    public synchronized void broadcastToAllExceptOne(String jsonToBroadcast, int playerNumber) {
        for (PlayerHandler player : players) {
            if (player.getPlayerNumber() != playerNumber) {
                player.sendJsonReply(jsonToBroadcast);
            }
        }
    }

    public synchronized void sendToAPlayer(String jsonToSend, int playerNumber) {
        for (PlayerHandler player : players) {
            if (player.getPlayerNumber() == playerNumber) {
                player.sendJsonReply(jsonToSend);
            }
        }
    }

    public synchronized void broadcastCurrentGameState() {
        for (PlayerHandler player : players) {
            player.sendJsonReply(currentGameState(player.getPlayerNumber()));
        }
    }

    public String currentGameState(int playerNumber) {
        Gson gson = new Gson();
        GameState data = new GameState(board.getBoard(), (playerNumber - gamemode.getTurn() + numberOfPlayers) % numberOfPlayers, (gamemode.getTurn()-1+numberOfPlayers)%numberOfPlayers);
        return gson.toJson(data);
    }

    public synchronized void removePlayer(PlayerHandler player) {
        players.remove(player);
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }  

    public boolean setUpGamemode(String gamemodeName, int playerCount) {
        System.out.println("Setting up gamemode: " + gamemodeName);
        this.numberOfPlayers = playerCount;
        GameBuilder gameBuilder = new GameBuilder(gamemodeName);
        GamemodeBuilder gamemodeBuilder = gameBuilder.getGamemodeBuilder();
        BoardBuilder boardBuilder = gameBuilder.getBoardBuilder();
        gamemodeBuilder.buildGamemode(playerCount);
        boardBuilder.buildBoard(playerCount);
        if(gamemodeBuilder.getGamemode() == null || boardBuilder.getBoard() == null) {
            System.out.println("Gamemode setup failed.");
            return false;
        }
        this.gamemode = gamemodeBuilder.getGamemode();
        this.board = boardBuilder.getBoard();
        this.settingUp = false;
        System.out.println("Gamemode set up is done.");
        return true;
    }
}

class PlayerHandler implements Runnable {
    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Gamemode gamemode;
    private int playerNumber;
    private Boolean isConnected;
    private Gson gson;

    public PlayerHandler(Socket socket, Server server, int playerNumber) {
        this.socket = socket;
        this.server = server;
        if(server.getGamemode() != null) {
            this.gamemode = server.getGamemode();
        } else {
            this.gamemode = null;
        }
        this.playerNumber = playerNumber;   
        this.isConnected = true;
        this.gson = new Gson();
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            Gson gson = new Gson();
            if(gamemode == null) {
                while (isConnected && gamemode == null) {
                    setUpGamemode();
                }
            }
            while (isConnected) {
                String jsonString = (String) in.readObject(); 
                Command command = gson.fromJson(jsonString, Command.class);
                System.out.println("Player " + playerNumber + " sent command: " + command.getName());

                switch (command.getName()) {
                    case "move":
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
                        break;
                    case "display":
                        sendJsonReply(server.currentGameState(playerNumber));
                        break;
                    case "quit":
                        handleDisconnection();
                        break;
                    case "message":
                        if (command.getArgs().length == 1 && command.getTextArg() != null) {
                            if (command.getArgs()[0] == -1) {
                                server.broadcastToAllExceptOne(gson.toJson(new Message(command.getTextArg() + " FROM PLAYER: " + playerNumber)), playerNumber);
                            } else {
                                if(command.getArgs()[0] >= 0 && command.getArgs()[0] < server.getNumberOfPlayers()) {
                                    server.sendToAPlayer(gson.toJson(new Message(command.getTextArg() + " FROM PLAYER: " + playerNumber)), command.getArgs()[0]);
                                } else {
                                    sendErrorMessage("Invalid player number.");
                                }
                            }
                        } else {
                            sendErrorMessage("Invalid number of arguments for message.");
                        }
                        break;
                    default:
                        sendErrorMessage("Unknown command.");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Player " + playerNumber + " disconnected.");
            handleDisconnection();
        }
    }

    private void sendErrorMessage(String message) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(new ErrorMessage(message));
        try {
            out.writeObject(jsonString);
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

    private void handleDisconnection() {
        isConnected = false;
        server.removePlayer(this);
        try {
            socket.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    private void setUpGamemode() {
        try {
            out.writeObject(gson.toJson(new Message("Send setUpGameMode.")));
            out.flush();
            String jsonString = (String) in.readObject(); 
            Command command = gson.fromJson(jsonString, Command.class);
            System.out.println("Player " + playerNumber + " sent command: " + command.getName());
            switch (command.getName()) {
                case "setUpGamemode":
                    if(!server.setUpGamemode("DummyGame", command.getArgs()[0])) sendErrorMessage("Try again, invalid setup.");
                    gamemode = server.getGamemode();
                    break;
                default:
                    sendErrorMessage("Try again, invalid setup.");
                break;
            }
        } catch (IOException | ClassNotFoundException e) {
            sendErrorMessage(gson.toJson(new ErrorMessage(e.getMessage())));
        }
    }
}
