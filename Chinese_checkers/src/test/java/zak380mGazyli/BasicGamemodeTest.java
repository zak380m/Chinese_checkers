package zak380mGazyli;

import org.junit.jupiter.api.Test;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.BoardBuilders.ClassicBoardBuilder;
import zak380mGazyli.Gamemodes.BasicGamemode;
import zak380mGazyli.Gamemodes.Gamemode;

public class BasicGamemodeTest {

    @Test
    public void testPlayerSetup() {
        Gamemode gamemode = new BasicGamemode();
        for (int player_count : new int[]{2,3,4,6}) {
            BoardBuilder bb = new ClassicBoardBuilder();
            bb.buildBoard(player_count);
            Board board = bb.getBoard();
            board.displayBoard();
            gamemode.setNumberOfPlayers(player_count, board);
        }
    }

    @Test
    public void testValidateMove() {
        for (int player_count : new int[]{2,3,4,6}) {
            BoardBuilder bb = new ClassicBoardBuilder();
            bb.buildBoard(player_count);
            Board board = bb.getBoard();
            board.displayBoard();
            Gamemode gamemode = new BasicGamemode();
            gamemode.setNumberOfPlayers(player_count, board);
            randomMoveTest(gamemode, board);
            board.displayBoard();
        }
    }

    private void randomMoveTest(Gamemode gamemode, Board board) {
        if(gamemode.validateMove(10, 2, 8, 4, board)) gamemode.processMove(10, 2, 8, 4, board);
        if(gamemode.validateMove(20, 12, 18, 10, board)) gamemode.processMove(20, 12, 18, 10, board);
        if(gamemode.validateMove(4, 12, 6, 10, board)) gamemode.processMove(4, 12, 6, 10, board);
        gamemode.processPass();
        if(gamemode.validateMove(24, 12, 16, 12, board)) gamemode.processMove(24, 12, 16, 12, board);
    }
}
