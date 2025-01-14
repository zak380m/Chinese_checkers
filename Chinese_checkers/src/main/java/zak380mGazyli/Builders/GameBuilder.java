package zak380mGazyli.Builders;

import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.BoardBuilders.ClassicBoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.BasicGamemodeBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.DummyGamemodeBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.SuperGamemodeBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBuilder {
    private static final Map<String, GameComponents> gameList = new HashMap<>();
    private String gameName;
    private static final List<String> gameNameList = new ArrayList<>();

    public GameBuilder() {
        populateGamelist();
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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

    public static String[] getGameNames() {
        return gameList.keySet().toArray(new String[0]);
    }

    private void populateGamelist() {
        addGame("DummyGame", new DummyGamemodeBuilder(), new ClassicBoardBuilder());
        addGame("BasicGame", new BasicGamemodeBuilder(), new ClassicBoardBuilder());
        addGame("SuperGame", new SuperGamemodeBuilder(), new ClassicBoardBuilder());
        gameNameList.addAll(gameList.keySet());
    }

    public List<String> getGameList() {
        return gameNameList;
    }

    public List<String> getBoardList() {
        return new ArrayList<>();
    }
}