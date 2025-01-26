package zak380mGazyli.Misc;

import java.util.Map;

/**
 * The GameState class represents the state of the game at a specific point in time.
 */
public class GameState {
    private final int whoMoved;
    private final Cell[][] boardState;
    private final int playerNumber;
    private final String playerColor;
    private final int turnCount;
    private final Map<Integer, Integer> playerPlace;
    private final int currentPlayerTurn;
    private final boolean isPass;
    private final int gameID;

    /**
     * Constructs a GameState instance with the specified parameters.
     *
     * @param currentPlayerTurn The current player's turn.
     * @param boardState The state of the game board.
     * @param playerNumber The number of the player.
     * @param whoMoved The player who made the last move.
     * @param playerPlace The map of player places.
     * @param isPass Whether the last move was a pass.
     * @param playerColor The color of the player.
     * @param turnCount The number of turns taken.
     */
    public GameState(int currentPlayerTurn, Cell[][] boardState, int playerNumber, int whoMoved, Map<Integer, Integer> playerPlace, boolean isPass, String playerColor, int turnCount, int gameID) {
        this.currentPlayerTurn = currentPlayerTurn;
        this.boardState = boardState;
        this.playerNumber = playerNumber;
        this.whoMoved = whoMoved;
        this.playerPlace = playerPlace;
        this.isPass = isPass;
        this.playerColor = playerColor;
        this.turnCount = turnCount;
        this.gameID = gameID;
    }

    /**
     * Gets the player who made the last move.
     *
     * @return The player who made the last move.
     */
    public int getWhoMoved() {
        return whoMoved;
    }

    /**
     * Gets the state of the game board.
     *
     * @return The state of the game board.
     */
    public Cell[][] getBoardState() {
        return boardState;
    }

    /**
     * Gets the number of the player.
     *
     * @return The number of the player.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Gets the map of player places.
     *
     * @return The map of player places.
     */
    public Map<Integer, Integer> getPlayerPlace() {
        return playerPlace;
    }

    /**
     * Checks if the last move was a pass.
     *
     * @return true if the last move was a pass, false otherwise.
     */
    public boolean getIsPass() {
        return isPass;
    }

    /**
     * Gets the color of the player.
     *
     * @return The color of the player.
     */
    public String getPlayerColor() {
        return playerColor;
    }

    /**
     * Gets the number of turns taken.
     *
     * @return The number of turns taken.
     */
    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Gets the current player's turn.
     *
     * @return The current player's turn.
     */
    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public int getGameID() {
        return gameID;
    }
}
