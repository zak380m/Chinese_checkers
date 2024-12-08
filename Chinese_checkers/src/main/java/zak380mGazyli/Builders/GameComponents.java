// GameComponents.java
package zak380mGazyli.Builders;

import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;

public class GameComponents {
    private final GamemodeBuilder gamemodeBuilder;
    private final BoardBuilder boardBuilder;

    public GameComponents(GamemodeBuilder gamemodeBuilder, BoardBuilder boardBuilder) {
        this.gamemodeBuilder = gamemodeBuilder;
        this.boardBuilder = boardBuilder;
    }

    public GamemodeBuilder getGamemodeBuilder() {
        return gamemodeBuilder;
    }

    public BoardBuilder getBoardBuilder() {
        return boardBuilder;
    }
}