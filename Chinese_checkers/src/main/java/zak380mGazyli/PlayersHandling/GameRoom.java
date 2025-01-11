package zak380mGazyli.PlayersHandling;

import java.net.*;
import java.util.*;

import com.google.gson.Gson;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Bots.Bot;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.Misc.GameState;

public class GameRoom {
    private String password;
    private int roomId;
    private int numberOfPlayers;
    private int numberOfBots;
    private List<PlayerHandler> players;
    private List<Bot> bots;
    private Board board;
    private Gamemode gamemode;

    public GameRoom(Gamemode gamemode, Board board, String password, int numberOfPlayers, int numberOfBots, int roomId) {
        this.gamemode = gamemode;
        this.board = board;
        this.password = password;
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfBots = numberOfBots;
        this.roomId = roomId;
        bots = new ArrayList<>();
        players = new ArrayList<>();
        
    }

    public String getGamemodeName() {
        return gamemode.getName();
    }

    public String getPassword() {
        return password;
    }

    public int getRoomId() {
        return roomId;
    }

    public boolean areThereMissingPlayers() {
        return (numberOfPlayers - players.size() > 0);
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
}
