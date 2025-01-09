package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import zak380mGazyli.Boards.*;
import zak380mGazyli.Bots.Bot;
import zak380mGazyli.Bots.DummyBot;
import zak380mGazyli.Builders.GamemodeBuilders.*;
import zak380mGazyli.Builders.GameBuilder;
import zak380mGazyli.Builders.BoardBuilders.*;
import zak380mGazyli.Gamemodes.*;

public class Server {
    private List<PlayerHandler> players = new ArrayList<>();
    private List<Bot> bots = new ArrayList<>();
    private Gamemode gamemode;  
    private Board board;
    private int numberOfPlayers;
    private int numberOfBots;
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

        for(int i = 0; i < numberOfBots; i++) {
            Bot botHandler = new DummyBot();
            bots.add(botHandler);
            new Thread(botHandler).start();
        }

        while (numberOfPlayers - numberOfBots > players.size()) {
            Socket playerSocket2 = serverSocket.accept();
            System.out.println("New player connected.");
            PlayerHandler playerHandler2 = new PlayerHandler(playerSocket2, this, players.size() + numberOfBots);
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

    public synchronized void processPass() {
        gamemode.processPass();
        broadcastCurrentGameState();
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
        GameState data = new GameState(board.getBoard(), (playerNumber - gamemode.getTurn() + numberOfPlayers) % numberOfPlayers, (gamemode.getTurn()-1+numberOfPlayers)%numberOfPlayers
        , gamemode.playerPlace(playerNumber), gamemode.isPass());
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

    public boolean setUpGamemode(String gamemodeName, int playerCount, int botCount) {
        System.out.println("Setting up gamemode: " + gamemodeName);
        this.numberOfPlayers = playerCount;
        this.numberOfBots = botCount;
        GameBuilder gameBuilder = new GameBuilder(gamemodeName);
        GamemodeBuilder gamemodeBuilder = gameBuilder.getGamemodeBuilder();
        BoardBuilder boardBuilder = gameBuilder.getBoardBuilder();
        boardBuilder.buildBoard(playerCount);
        if(boardBuilder.getBoard() == null) {
            System.out.println("Board setup failed.");
            return false;
        }
        this.board = boardBuilder.getBoard();
        gamemodeBuilder.buildGamemode(playerCount, board);
        if(gamemodeBuilder.getGamemode() == null) {
            System.out.println("Gamemode setup failed.");
            return false;
        }
        this.gamemode = gamemodeBuilder.getGamemode();
        this.settingUp = false;
        System.out.println("Gamemode set up is done.");
        return true;
    }
}
