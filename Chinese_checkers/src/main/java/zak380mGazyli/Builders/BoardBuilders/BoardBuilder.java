package zak380mGazyli.Builders.BoardBuilders;

import zak380mGazyli.Boards.Board;

/**
 * The BoardBuilder interface defines the methods required to build a game board.
 */
public interface BoardBuilder {
    /**
     * Builds the board with the specified number of players.
     *
     * @param players The number of players.
     */
    void buildBoard(int players);

    /**
     * Gets the built board.
     *
     * @return The built board.
     */
    Board getBoard();
}
