package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import zak380mGazyli.Boards.*;
import zak380mGazyli.Builders.GamemodeBuilders.*;
import zak380mGazyli.Builders.BoardBuilders.*;
import zak380mGazyli.Gamemodes.*;
import zak380mGazyli.Messages.ErrorMessage;

public class Server {
    private GamemodeBuilder gamemodeBuilder = new DummyGamemodeBuilder();
    private BoardBuilder boardBuilder = new ClassicBoardBuilder();
    private List<PlayerHandler> players = new ArrayList<>();
    private Gamemode gamemode;  
    private Board board;
    private int numberOfPlayers;

    public static void main(String[] args) throws IOException {
        new Server().startServer();
    }

    public void startServer() throws IOException {
        gamemodeBuilder.buildGamemode();
        gamemode = new DummyGamemode();
        boardBuilder.buildBoard(2);
        board = boardBuilder.getBoard();
        ServerSocket serverSocket = new ServerSocket(5555);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Server started on port 5555.");
        System.out.println("How many players should be connected to start the game?");
        numberOfPlayers = scanner.nextInt();
        scanner.close();

        while (numberOfPlayers > 0) {
            Socket playerSocket = serverSocket.accept();
            System.out.println("New player connected.");
            PlayerHandler playerHandler = new PlayerHandler(playerSocket, this, this.gamemode, players.size());
            players.add(playerHandler);
            new Thread(playerHandler).start();
            numberOfPlayers--;
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
        GameState data = new GameState(board.getBoard(), playerNumber);
        return gson.toJson(data);
    }

    public synchronized void removePlayer(PlayerHandler player) {
        players.remove(player);
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

    public PlayerHandler(Socket socket, Server server, Gamemode gamemode, int playerNumber) {
        this.socket = socket;
        this.server = server;
        this.gamemode = gamemode;
        this.playerNumber = playerNumber;   
        this.isConnected = true;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            Gson gson = new Gson();
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
}
