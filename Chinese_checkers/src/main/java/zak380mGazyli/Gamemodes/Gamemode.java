package zak380mGazyli.Gamemodes;

import java.util.Map;

import zak380mGazyli.Boards.Board;

/**
 * The Gamemode interface defines the methods required for implementing different game modes.
 */
public interface Gamemode {
    /**
     * Sets the number of players for the game mode.
     *
     * @param numberOfPlayers The number of players.
     * @param board The game board.
     * @return true if the number of players is valid, false otherwise.
     */
    public boolean setNumberOfPlayers(int numberOfPlayers, Board board);

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
    public boolean validateMove(int startX, int startY, int endX, int endY, Board board);

    /**
     * Processes a move by updating the board and advancing the game state.
     *
     * @param startX The starting X coordinate.
     * @param startY The starting Y coordinate.
     * @param endX The ending X coordinate.
     * @param endY The ending Y coordinate.
     * @param board The game board.
     */
    public void processMove(int startX, int startY, int endX, int endY, Board board);

    /**
     * Processes a pass move by advancing the turn and setting the pass flag.
     */
    public void processPass();

    /**
     * Checks if the current move is a pass.
     *
     * @return true if the current move is a pass, false otherwise.
     */
    public boolean isPass();

    /**
     * Gets the player places.
     *
     * @return A map of player places.
     */
    public Map<Integer, Integer> playerPlace();

    /**
     * Gets the current turn number.
     *
     * @return The current turn number.
     */
    public int getTurn();

    /**
     * Gets the name of the game mode.
     *
     * @return The name of the game mode.
     */
    public String getName();

    /**
     * Gets the turn count.
     *
     * @return The turn count.
     */
    public int getTurnCount();

    /**
     * Gets the color of the specified player.
     *
     * @param playerNumber The player number.
     * @return The color of the player.
     */
    public String getPlayerColor(int playerNumber);
}
