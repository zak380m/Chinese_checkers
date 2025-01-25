package zak380mGazyli.Gamemodes;

import java.util.HashMap;
import java.util.Map;

import zak380mGazyli.Boards.*;
import zak380mGazyli.Misc.Color;

/**
 * The DummyGamemode class implements the Gamemode interface and provides a dummy game mode for testing purposes.
 */
public class DummyGamemode implements Gamemode {

    private int turn = 0;
    private int numberOfPlayers = 1;
    private boolean pass = false;
    private Map<Integer, Integer> playerPlace;

    /**
     * Constructs a new DummyGamemode instance.
     * Initializes the player place map.
     */
    public DummyGamemode() {
        this.playerPlace = new HashMap<>();
    }

    /**
     * Gets the name of the game mode.
     *
     * @return The name of the game mode.
     */
    @Override
    public String getName() {
        return "DummyGame";
    }

    /**
     * Sets the number of players for the game mode.
     *
     * @param numberOfPlayers The number of players.
     * @param board The game board.
     * @return true if the number of players is valid, false otherwise.
     */
    @Override
    public boolean setNumberOfPlayers(int numberOfPlayers, Board board) {
        if(numberOfPlayers < 2 || numberOfPlayers > 6 || numberOfPlayers == 5) {
            return false;
        }
        this.numberOfPlayers = numberOfPlayers;
        return true;
    }

    /**
     * Validates a move based on the start and end coordinates and the board state.
     *
     * @param startX The starting X coordinate.
     * @param startY The starting Y coordinate.
     * @param endX The ending X coordinate.
     * @param endY The ending Y coordinate.
     * @param board The game board.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean validateMove(int startX, int startY, int endX, int endY, Board board) {
        return true;
    }

    /**
     * Processes a move by updating the board and advancing the turn.
     *
     * @param startX The starting X coordinate.
     * @param startY The starting Y coordinate.
     * @param endX The ending X coordinate.
     * @param endY The ending Y coordinate.
     * @param board The game board.
     */
    @Override
    public void processMove(int startX, int startY, int endX, int endY, Board board) {
        board.updateBoard(startX, startY, endX, endY);
        turn++;
        pass = false;
    }

    /**
     * Processes a pass move by advancing the turn and setting the pass flag.
     */
    @Override
    public void processPass() {
        turn++;
        pass = true;
    }

    /**
     * Gets the current turn number.
     *
     * @return The current turn number.
     */
    @Override
    public int getTurn() {
        return turn % numberOfPlayers;
    }

    /**
     * Checks if the current move is a pass.
     *
     * @return true if the current move is a pass, false otherwise.
     */
    @Override
    public boolean isPass() {
        return pass;
    }

    /**
     * Gets the player places.
     *
     * @return A map of player places.
     */
    @Override
    public Map<Integer, Integer> playerPlace() {
        return playerPlace;
    }

    /**
     * Gets the turn count.
     *
     * @return The turn count.
     */
    @Override
    public int getTurnCount() {
        return turn;
    }

    @Override
    public void setTurnCount(int turnCount) {
        this.turn = turnCount;
    }

    /**
     * Gets the color of the specified player.
     *
     * @param playerNumber The player number.
     * @return The color of the player.
     */
    @Override
    public String getPlayerColor(int playerNumber) {
        return Color.BLUE;
    }
}
