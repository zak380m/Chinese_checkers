package zak380mGazyli.Builders.GamemodeBuilders;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.Gamemode;

/**
 * Interface for building different game modes.
 */
public interface GamemodeBuilder {

    /**
     * Builds the game mode with the specified number of players and board.
     *
     * @param numberOfPlayers the number of players in the game
     * @param board the game board
     */
    void buildGamemode(int numberOfPlayers, Board board);

    /**
     * Returns the constructed game mode.
     *
     * @return the constructed game mode
     */
    Gamemode getGamemode();
}
