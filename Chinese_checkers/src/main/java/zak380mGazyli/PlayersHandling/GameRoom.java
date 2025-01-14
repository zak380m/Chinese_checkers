package zak380mGazyli.PlayersHandling;

import java.util.*;

import com.google.gson.Gson;

import zak380mGazyli.Server;
import zak380mGazyli.Boards.Board;
import zak380mGazyli.Bots.Bot;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.Misc.GameState;

public class GameRoom {
    private Server server;
    private String password;
    private int roomId;
    private int numberOfPlayers;
    private int numberOfBots;
    private Map<Integer, PlayerHandler> players;
    private Map<Integer, Bot> bots;
    private Board board;
    private Gamemode gamemode;
    private int realNumberOfPlayers;

    public GameRoom(Gamemode gamemode, Board board, String password, int numberOfPlayers, int numberOfBots, int roomId, Server server) {
        this.gamemode = gamemode;
        this.server = server;
        this.board = board;
        this.password = password;
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfBots = numberOfBots;
        this.roomId = roomId;
        this.realNumberOfPlayers = numberOfBots;
        bots = new HashMap<>();
        players = new HashMap<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.put(i, null);
        }
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
        boolean areThereMissingPlayers = false;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i) == null) {
                areThereMissingPlayers = true;
                break;
            }
        }
        return areThereMissingPlayers;
    }

    public synchronized boolean addPlayer(PlayerHandler playerHandler) {
        boolean areThereMissingPlayers = false;
        int index = 0;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i) == null) {
                index = i;
                areThereMissingPlayers = true;
                break;
            }
        }
        if (!areThereMissingPlayers) {
            return false;
        }
        players.put(index, playerHandler);
        playerHandler.setPlayerInRoom(index, this, gamemode);
        realNumberOfPlayers++;
        if(realNumberOfPlayers == numberOfPlayers) {
            for(int i = 0; i < numberOfPlayers; i++) {
                System.out.println("Player " + i + " index : " + players.get(i).getPlayerNumber());
            }
            broadcastCurrentGameState();
        }
        System.out.println("Player added to room: " + roomId);
        return true;
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
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i).getPlayerNumber() != playerNumber) {
                players.get(i).sendJsonReply(jsonToBroadcast);
            }
        }
    }

    public synchronized void sendToAPlayer(String jsonToSend, int playerNumber) {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i).getPlayerNumber() == playerNumber) {
                players.get(i).sendJsonReply(jsonToSend);
            }
        }
    }

    public synchronized void broadcastCurrentGameState() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if(players.get(i) != null) players.get(i).sendJsonReply(currentGameState(players.get(i).getPlayerNumber()));
        }
    }

    public String currentGameState(int playerNumber) {
        Gson gson = new Gson();
        GameState data = new GameState(board.getBoard(), playerNumber, (gamemode.getTurn()-1+numberOfPlayers)%numberOfPlayers
        , gamemode.playerPlace(), gamemode.isPass(), gamemode.getPlayerColor(playerNumber), gamemode.getTurnCount());
        return gson.toJson(data);
    }

    public synchronized void removePlayer(int index) {
        System.out.println("yolo I'm (" + index + ") leaving the game room " + roomId);
        players.put(index, null);
        realNumberOfPlayers--;
        if(isGameFinished() && realNumberOfPlayers == 0) {
            System.out.println("Game room " + roomId + " is finished.");
            server.deleteGameRoom(this);
        }
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }  

    public boolean isGameFinished() {
        for(int i = 0; i < numberOfBots + numberOfPlayers; i++) {
            if(gamemode.playerPlace().get(i) == 0) {
                return false;
            }
        }
        return true;
    }
}
