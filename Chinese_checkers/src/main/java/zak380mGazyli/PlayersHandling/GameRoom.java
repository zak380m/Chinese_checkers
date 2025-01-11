package zak380mGazyli.PlayersHandling;

import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.Gson;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Bots.Bot;
import zak380mGazyli.Builders.GameBuilder;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.Misc.GameState;

public class GameRoom {
    private String gameName;
    private String password;
    private int roomId;
    private int numberOfPlayers;
    private int numberOfBots;
    private List<PlayerHandler> players;
    private List<Bot> bots;
    private Board board;
    private Gamemode gamemode;

    public GameRoom(String gameName, String password, int numberOfPlayers, int numberOfBots, int roomId) {
        this.gameName = gameName;
        this.password = password;
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfBots = numberOfBots;
        this.roomId = roomId;
        bots = new ArrayList<>();
        players = new ArrayList<>();
        // Setup the game mode and board here (similar to your original code)
    }

    public String getGameName() {
        return gameName;
    }

    public String getPassword() {
        return password;
    }

    public int getRoomId() {
        return roomId;
    }

    public synchronized void addPlayer(Socket playerSocket) {
        PlayerHandler playerHandler = new PlayerHandler(playerSocket, this, players.size());
        players.add(playerHandler);
        new Thread(playerHandler).start();
        System.out.println("Player added to room: " + roomId);
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
        , gamemode.playerPlace(), gamemode.isPass());
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
        System.out.println("Gamemode set up is done.");
        return true;
    }
}
