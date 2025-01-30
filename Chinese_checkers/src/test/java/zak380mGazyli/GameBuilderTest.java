package zak380mGazyli;

import org.junit.jupiter.api.Test;
import zak380mGazyli.Builders.BoardBuilders.ClassicBoardBuilder;
import zak380mGazyli.Builders.GameBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.DummyGamemodeBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBuilderTest {
    @Test
    public void testSetGamemodeName() {
        GameBuilder gameBuilder = new GameBuilder();
        gameBuilder.setGamemodeName("BasicGame");
        assertEquals("BasicGamemodeBuilder", gameBuilder.getGamemodeBuilder().getClass().getSimpleName());
    }

    @Test
    public void testSetBoardName() {
        GameBuilder gameBuilder = new GameBuilder();
        gameBuilder.setBoardName("Classic");
        assertEquals("ClassicBoardBuilder", gameBuilder.getBoardBuilder().getClass().getSimpleName());
    }

    @Test
    public void testRegisterGamemodeBuilder() {
        GameBuilder gameBuilder = new GameBuilder();
        gameBuilder.registerGamemodeBuilder("DummyGame2", new DummyGamemodeBuilder());
        gameBuilder.setGamemodeName("DummyGame2");
        assertEquals("DummyGamemodeBuilder", gameBuilder.getGamemodeBuilder().getClass().getSimpleName());
    }

    @Test
    public void testRegisterBoardBuilder() {
        GameBuilder gameBuilder = new GameBuilder();
        gameBuilder.registerBoardBuilder("Classic2", new ClassicBoardBuilder());
        gameBuilder.setBoardName("Classic2");
        assertEquals("ClassicBoardBuilder", gameBuilder.getBoardBuilder().getClass().getSimpleName());
    }

    @Test
    public void testGetGamemodesList() {
        GameBuilder gameBuilder = new GameBuilder();
        gameBuilder.registerGamemodeBuilder("DummyGame2", new DummyGamemodeBuilder());
        assertTrue(gameBuilder.getGamemodesList().contains("DummyGame2"));
    }

    @Test
    public void testGetBoardsList() {
        GameBuilder gameBuilder = new GameBuilder();
        gameBuilder.registerBoardBuilder("Classic2", new ClassicBoardBuilder());
        assertTrue(gameBuilder.getBoardsList().contains("Classic2"));
    }
}
