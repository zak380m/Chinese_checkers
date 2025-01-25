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
    private String gameName;
    private String boardName;
    private final Map<String, GamemodeBuilder> gamemodeBuilders = new HashMap<>();
    private final Map<String, BoardBuilder> boardBuilders = new HashMap<>();

    public GameBuilder() {
        populateGamemodelist();
        populateBoardList();
    }

    public void setGamemodeName(String gameName) {
        this.gameName = gameName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public GamemodeBuilder getGamemodeBuilder() {
        return gamemodeBuilders.get(gameName);
    }

    public BoardBuilder getBoardBuilder() {
        return boardBuilders.get(boardName);
    }

    private void populateGamemodelist() {
        gamemodeBuilders.put("BasicGame", new BasicGamemodeBuilder());
        gamemodeBuilders.put("SuperGame", new SuperGamemodeBuilder());
        gamemodeBuilders.put("DummyGame", new DummyGamemodeBuilder());
    }

    public void registerGamemodeBuilder(String gamemodeName, GamemodeBuilder gamemodeBuilder) {
        gamemodeBuilders.put(gamemodeName, gamemodeBuilder);
    }

    private void populateBoardList() {
        boardBuilders.put("ClassicBoard", new ClassicBoardBuilder());
    }

    public void registerBoardBuilder(String boardName, BoardBuilder boardBuilder) {
        boardBuilders.put(boardName, boardBuilder);
    }

    public List<String> getGamemodesList() {
        return new ArrayList<>(gamemodeBuilders.keySet());
    }

    public List<String> getBoardsList() {
        return new ArrayList<>(boardBuilders.keySet());
    }
}
