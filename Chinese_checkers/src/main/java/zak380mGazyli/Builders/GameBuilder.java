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
 * The GameBuilder class is responsible for constructing game modes and boards.
 */
public class GameBuilder {
    private String gameName;
    private String boardName;
    private final Map<String, GamemodeBuilder> gamemodeBuilders = new HashMap<>();
    private final Map<String, BoardBuilder> boardBuilders = new HashMap<>();

    /**
     * Constructs a GameBuilder and populates the lists of game modes and boards.
     */
    public GameBuilder() {
        populateGamemodelist();
        populateBoardList();
    }

    /**
     * Sets the name of the game mode.
     *
     * @param gameName the name of the game mode
     */
    public void setGamemodeName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Sets the name of the board.
     *
     * @param boardName the name of the board
     */
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    /**
     * Gets the game mode builder for the current game mode.
     *
     * @return the game mode builder
     */
    public GamemodeBuilder getGamemodeBuilder() {
        return gamemodeBuilders.get(gameName);
    }

    /**
     * Gets the board builder for the current board.
     *
     * @return the board builder
     */
    public BoardBuilder getBoardBuilder() {
        return boardBuilders.get(boardName);
    }

    /**
     * Populates the list of game modes with predefined game mode builders.
     */
    private void populateGamemodelist() {
        gamemodeBuilders.put("BasicGame", new BasicGamemodeBuilder());
        gamemodeBuilders.put("SuperGame", new SuperGamemodeBuilder());
        gamemodeBuilders.put("DummyGame", new DummyGamemodeBuilder());
    }

    /**
     * Registers a new game mode builder.
     *
     * @param gamemodeName the name of the game mode
     * @param gamemodeBuilder the game mode builder
     */
    public void registerGamemodeBuilder(String gamemodeName, GamemodeBuilder gamemodeBuilder) {
        gamemodeBuilders.put(gamemodeName, gamemodeBuilder);
    }

    /**
     * Populates the list of boards with predefined board builders.
     */
    private void populateBoardList() {
        boardBuilders.put("Classic", new ClassicBoardBuilder());
    }

    /**
     * Registers a new board builder.
     *
     * @param boardName the name of the board
     * @param boardBuilder the board builder
     */
    public void registerBoardBuilder(String boardName, BoardBuilder boardBuilder) {
        boardBuilders.put(boardName, boardBuilder);
    }

    /**
     * Gets the list of available game modes.
     *
     * @return a list of game mode names
     */
    public List<String> getGamemodesList() {
        return new ArrayList<>(gamemodeBuilders.keySet());
    }

    /**
     * Gets the list of available boards.
     *
     * @return a list of board names
     */
    public List<String> getBoardsList() {
        return new ArrayList<>(boardBuilders.keySet());
    }
}
