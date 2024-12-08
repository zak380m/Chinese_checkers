package zak380mGazyli.Builders;

import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;

import java.util.HashMap;
import java.util.Map;

public class GameBuilder {
    private final Map<GamemodeBuilder, BoardBuilder> gameList = new HashMap<>();

    public void addGame(GamemodeBuilder gamemodeBuilder, BoardBuilder boardBuilder) {
        gameList.put(gamemodeBuilder, boardBuilder);
    }
}
