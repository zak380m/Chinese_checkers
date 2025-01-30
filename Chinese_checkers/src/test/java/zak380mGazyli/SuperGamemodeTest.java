package zak380mGazyli;

import org.junit.jupiter.api.Test;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.BoardBuilders.ClassicBoardBuilder;
import zak380mGazyli.Gamemodes.SuperGamemode;
import zak380mGazyli.Gamemodes.Gamemode;

public class SuperGamemodeTest {

    @Test
    public void testPlayerSetup() {
        Gamemode gamemode = new SuperGamemode();
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
            Gamemode gamemode = new SuperGamemode();
            gamemode.setNumberOfPlayers(player_count, board);
            randomMoveTest(gamemode, board);
            board.displayBoard();
        }
    }

    private void randomMoveTest(Gamemode gamemode, Board board) {
        if(gamemode.validateMove(12, 0, 16, 4, board)) gamemode.processMove(12, 0, 16, 4, board);
        if(gamemode.validateMove(23, 5, 8, 4, board)) gamemode.processMove(23, 5, 8, 4, board);
        if(gamemode.validateMove(24, 12, 12, 0, board)) gamemode.processMove(24, 12, 12, 0, board);
        if(gamemode.validateMove(12, 16, 23, 5, board)) gamemode.processMove(12, 16, 23, 5, board);
        if(gamemode.validateMove(0, 12, 24, 12, board)) gamemode.processMove(0, 12, 24, 12, board);
        if(gamemode.validateMove(0, 4, 12, 16, board)) gamemode.processMove(0, 4, 12, 16, board);
        if(gamemode.validateMove(11, 1, 14, 4, board)) gamemode.processMove(11, 1, 14, 4, board);
    }
}
