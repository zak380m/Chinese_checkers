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

    public synchronized void broadcastCurrentGameState() {
        for (PlayerHandler player : players) {
            player.sendCurrentGameState(currentGameState(player.getPlayerNumber()));
        }
    }

    public String currentGameState(int playerNumber) {
        Gson gson = new Gson();
        GameState data = new GameState(board.getBoard(), (playerNumber - gamemode.getTurn() + numberOfPlayers) % numberOfPlayers);
        return gson.toJson(data);
    }

    public synchronized void removePlayer(PlayerHandler player) {
        players.remove(player);
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public void setUpGamemode(String gamemodeName, int playerCount) {
        System.out.println("Setting up gamemode: " + gamemodeName);
        this.numberOfPlayers = playerCount;
        GameBuilder gameBuilder = new GameBuilder(gamemodeName);
        GamemodeBuilder gamemodeBuilder = gameBuilder.getGamemodeBuilder();
        BoardBuilder boardBuilder = gameBuilder.getBoardBuilder();
        gamemodeBuilder.buildGamemode(playerCount);
        boardBuilder.buildBoard(playerCount);
        this.gamemode = gamemodeBuilder.getGamemode();
        this.board = boardBuilder.getBoard();
        this.settingUp = false;
        System.out.println("Gamemode set up is done.");
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
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            Gson gson = new Gson();
            if(gamemode == null) {
                setUpGamemode();
                while (isConnected && gamemode == null) {
                    String jsonString = (String) in.readObject(); 
                    Command command = gson.fromJson(jsonString, Command.class);
    
                    System.out.println("Player " + playerNumber + " sent command: " + command.getName());
    
                    switch (command.getName()) {
                        case "setUpGamemode":
                            server.setUpGamemode("DummyGame", 2);
                            gamemode = server.getGamemode();
                            break;
                        default:
                            sendErrorMessage("Send setUpGameMode.");
                            break;
                    }
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
                        sendCurrentGameState(server.currentGameState(playerNumber));
                        break;

                    case "quit":
                        handleDisconnection();
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

    public void sendCurrentGameState(String currentGameState) {
        try {
            out.writeObject(currentGameState);
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
            Gson gson = new Gson();
            out.writeObject(gson.toJson(new Message("setUpGamemode")));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
