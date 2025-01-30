package zak380mGazyli.PlayersHandling;

import java.util.*;

import com.google.gson.Gson;

import zak380mGazyli.Mediator;
import zak380mGazyli.Server;
import zak380mGazyli.Boards.Board;
import zak380mGazyli.Bots.BasicBot;
import zak380mGazyli.Bots.Bot;
import zak380mGazyli.Database.Models.Game;
import zak380mGazyli.Database.Models.Move;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.Misc.GameState;

/**
 * The GameRoom class represents a game room where players and bots can join and play a game.
 */
public class GameRoom {
    private final Server server;
    private String password;
    private final int roomId;
    private final int numberOfPlayers;
    private final int numberOfBots;
    private final Map<Integer, PlayerHandler> players;
    private final Map<Integer, Bot> bots;
    private final Board board;
    private final Gamemode gamemode;
    private final Mediator mediator;
    private int realNumberOfPlayers;
    private Game game;

    /**
     * Constructs a GameRoom instance.
     *
     * @param gamemode The game mode to be used in the game room.
     * @param board The board to be used in the game room.
     * @param password The password for the game room.
     * @param numberOfPlayers The number of players in the game room.
     * @param numberOfBots The number of bots in the game room.
     * @param roomId The ID of the game room.
     * @param server The server managing the game room.
     * @param mediator The mediator for handling game moves.
     */
    public GameRoom(Gamemode gamemode, Board board, String password, int numberOfPlayers, int numberOfBots, int roomId, Server server, Mediator mediator) {
        this.gamemode = gamemode;
        this.server = server;
        this.board = board;
        this.password = password;
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfBots = numberOfBots;
        this.roomId = roomId;
        this.mediator = mediator;
        bots = new HashMap<>();
        players = new HashMap<>();
        realNumberOfPlayers = 0;
        for (int i = 0; i < numberOfPlayers; i++) {
            players.put(i, null);
        }
        for(int i = 0; i < numberOfBots; i++) {
            bots.put(i, new BasicBot());
            bots.get(i).setGame(this, gamemode, board, i + numberOfPlayers);
            new Thread(bots.get(i)).start();
        }
    }

    /**
     * Sets the game instance for the game room.
     *
     * @param game The game instance to set.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets the game instance of the game room.
     *
     * @return The game instance.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets the name of the game mode.
     *
     * @return The name of the game mode.
     */
    public String getGamemodeName() {
        return gamemode.getName();
    }

    /**
     * Gets the password of the game room.
     *
     * @return The password of the game room.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the ID of the game room.
     *
     * @return The ID of the game room.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Gets the board of the game room.
     *
     * @return The board of the game room.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Checks if there are missing players in the game room.
     *
     * @return true if there are missing players, false otherwise.
     */
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

    /**
     * Adds a player to the game room.
     *
     * @param playerHandler The player handler to add.
     * @return true if the player was added successfully, false otherwise.
     */
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
        System.out.println("Real players in room(" + roomId + "): " + realNumberOfPlayers);
        if(realNumberOfPlayers == numberOfPlayers) {
            for(int i = 0; i < numberOfPlayers; i++) {
                System.out.println("Player " + i + " index : " + players.get(i).getPlayerNumber());
            }
            broadcastCurrentGameState();
        }
        System.out.println("Player added to room: " + roomId);
        return true;
    }

    /**
     * Processes a move in the game room.
     *
     * @param startX The starting X coordinate of the move.
     * @param startY The starting Y coordinate of the move.
     * @param endX The ending X coordinate of the move.
     * @param endY The ending Y coordinate of the move.
     */
    public synchronized void processMove(int startX, int startY, int endX, int endY) {
        if (gamemode.validateMove(startX, startY, endX, endY, board)) {
            gamemode.processMove(startX, startY, endX, endY, board);
            Move move = new Move(game, gamemode.getTurnCount(), startX, startY, endX, endY, false);
            mediator.addMove(move);
            broadcastCurrentGameState();
        }
    }

    /**
     * Processes a pass in the game room.
     */
    public synchronized void processPass() {
        gamemode.processPass();
        Move move = new Move(game, gamemode.getTurnCount(), 0, 0, 0, 0, true);
        mediator.addMove(move);
        broadcastCurrentGameState();
    }

    /**
     * Broadcasts a message to all players except one.
     *
     * @param jsonToBroadcast The JSON message to broadcast.
     * @param playerNumber The player number to exclude from the broadcast.
     */
    public synchronized void broadcastToAllExceptOne(String jsonToBroadcast, int playerNumber) {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i).getPlayerNumber() != playerNumber) {
                players.get(i).sendJsonReply(jsonToBroadcast);
            }
        }
    }

    /**
     * Sends a message to a specific player.
     *
     * @param jsonToSend The JSON message to send.
     * @param playerNumber The player number to send the message to.
     */
    public synchronized void sendToAPlayer(String jsonToSend, int playerNumber) {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i).getPlayerNumber() == playerNumber) {
                players.get(i).sendJsonReply(jsonToSend);
            }
        }
    }

    /**
     * Broadcasts the current game state to all players.
     */
    public synchronized void broadcastCurrentGameState() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if(players.get(i) != null) players.get(i).sendJsonReply(currentGameState(players.get(i).getPlayerNumber()));
        }
    }

    /**
     * Gets the current game state for a specific player.
     *
     * @param playerNumber The player number to get the game state for.
     * @return The current game state as a JSON string.
     */
    public String currentGameState(int playerNumber) {
        Gson gson = new Gson();
        GameState data = new GameState(gamemode.getTurn(), board.getBoard(), playerNumber, (gamemode.getTurn()-1+numberOfPlayers)%numberOfPlayers
        , gamemode.playerPlace(), gamemode.isPass(), gamemode.getPlayerColor(playerNumber), gamemode.getTurnCount(), game.getId());
        return gson.toJson(data);
    }

    /**
     * Removes a player from the game room.
     *
     * @param index The index of the player to remove.
     */
    public synchronized void removePlayer(int index) {
        System.out.println("yolo I'm (" + index + ") leaving the game room " + roomId);
        players.put(index, null);
        realNumberOfPlayers--;
        if(isGameFinished() && realNumberOfPlayers == 0) {
            System.out.println("Game room " + roomId + " is finished.");
            server.deleteGameRoom(this);
        }
    }

    /**
     * Gets the game mode of the game room.
     *
     * @return The game mode.
     */
    public Gamemode getGamemode() {
        return gamemode;
    }

    /**
     * Gets the total number of players (including bots) in the game room.
     *
     * @return The total number of players.
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers + numberOfBots;
    }

    /**
     * Checks if the game is finished.
     *
     * @return true if the game is finished, false otherwise.
     */
    public boolean isGameFinished() {
        for(int i = 0; i < numberOfBots + numberOfPlayers; i++) {
            if(gamemode.playerPlace().get(i) == 0) {
                return false;
            }
        }
        return true;
    }
}
