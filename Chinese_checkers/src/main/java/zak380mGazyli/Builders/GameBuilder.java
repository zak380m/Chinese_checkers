package zak380mGazyli.Builders;

import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.BoardBuilders.ClassicBoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.BasicGamemodeBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.DummyGamemodeBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.SuperGamemodeBuilder;
import zak380mGazyli.Gamemodes.SuperGamemode;

import java.util.HashMap;
import java.util.Map;

public class GameBuilder {
    private final Map<String, GameComponents> gameList = new HashMap<>();
    private final String gameName;

    public GameBuilder(String gameName) {
        this.gameName = gameName;
        populateGamelist();
    }

    public void addGame(String name, GamemodeBuilder gamemodeBuilder, BoardBuilder boardBuilder) {
        gameList.put(name, new GameComponents(gamemodeBuilder, boardBuilder));
    }

    public GamemodeBuilder getGamemodeBuilder() {
        GameComponents components = gameList.get(gameName);
        return components != null ? components.getGamemodeBuilder() : null;
    }

    public BoardBuilder getBoardBuilder() {
        GameComponents components = gameList.get(gameName);
        return components != null ? components.getBoardBuilder() : null;
    }

    private void populateGamelist() {
        addGame("DummyGame", new DummyGamemodeBuilder(), new ClassicBoardBuilder());
        addGame("BasicGame", new BasicGamemodeBuilder(), new ClassicBoardBuilder());
        addGame("SuperGame", new SuperGamemodeBuilder(), new ClassicBoardBuilder());
    }
}