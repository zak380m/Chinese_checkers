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

/**
 * The GameBuilder class is responsible for building game configurations
 * by associating game modes with board builders.
 */
public class GameBuilder {
    private static final Map<String, GameComponents> gameList = new HashMap<>();
    private String gameName;
    private static final List<String> gameNameList = new ArrayList<>();

    /**
     * Constructs a GameBuilder instance and populates the game list.
     */
    public GameBuilder() {
        if(gameNameList.isEmpty())populateGamelist();
    }

    /**
     * Sets the name of the game to be built.
     *
     * @param gameName The name of the game.
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Adds a game configuration to the game list.
     *
     * @param name The name of the game.
     * @param gamemodeBuilder The builder for the game mode.
     * @param boardBuilder The builder for the board.
     */
    public void addGame(String name, GamemodeBuilder gamemodeBuilder, BoardBuilder boardBuilder) {
        gameList.put(name, new GameComponents(gamemodeBuilder, boardBuilder));
    }

    /**
     * Gets the game mode builder for the current game.
     *
     * @return The game mode builder, or null if the game is not found.
     */
    public GamemodeBuilder getGamemodeBuilder() {
        GameComponents components = gameList.get(gameName);
        return components != null ? components.getGamemodeBuilder() : null;
    }

    /**
     * Gets the board builder for the current game.
     *
     * @return The board builder, or null if the game is not found.
     */
    public BoardBuilder getBoardBuilder() {
        GameComponents components = gameList.get(gameName);
        return components != null ? components.getBoardBuilder() : null;
    }

    /**
     * Gets the names of all available games.
     *
     * @return An array of game names.
     */
    public static String[] getGameNames() {
        return gameList.keySet().toArray(new String[0]);
    }

    /**
     * Populates the game list with predefined game configurations.
     */
    private void populateGamelist() {
        addGame("DummyGame", new DummyGamemodeBuilder(), new ClassicBoardBuilder());
        addGame("BasicGame", new BasicGamemodeBuilder(), new ClassicBoardBuilder());
        addGame("SuperGame", new SuperGamemodeBuilder(), new ClassicBoardBuilder());
        gameNameList.addAll(gameList.keySet());
    }

    /**
     * Gets the list of game names.
     *
     * @return A list of game names.
     */
    public List<String> getGameList() {
        return gameNameList;
    }

    /**
     * Gets the list of board names.
     *
     * @return An empty list of board names.
     */
    public List<String> getBoardList() {
        return new ArrayList<>();
    }
}
