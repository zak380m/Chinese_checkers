// GameComponents.java
package zak380mGazyli.Builders;

import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;

/**
 * The GameComponents class encapsulates the components required to build a game,
 * including the game mode builder and the board builder.
 */
public class GameComponents {
    private final GamemodeBuilder gamemodeBuilder;
    private final BoardBuilder boardBuilder;

    /**
     * Constructs a GameComponents instance with the specified game mode builder and board builder.
     *
     * @param gamemodeBuilder The builder for the game mode.
     * @param boardBuilder The builder for the board.
     */
    public GameComponents(GamemodeBuilder gamemodeBuilder, BoardBuilder boardBuilder) {
        this.gamemodeBuilder = gamemodeBuilder;
        this.boardBuilder = boardBuilder;
    }

    /**
     * Gets the game mode builder.
     *
     * @return The game mode builder.
     */
    public GamemodeBuilder getGamemodeBuilder() {
        return gamemodeBuilder;
    }

    /**
     * Gets the board builder.
     *
     * @return The board builder.
     */
    public BoardBuilder getBoardBuilder() {
        return boardBuilder;
    }
}
