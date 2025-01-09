package zak380mGazyli;

import org.junit.jupiter.api.Test;
import zak380mGazyli.Boards.Board;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.BoardBuilders.ClassicBoardBuilder;

public class ClassicBoardTest {
//    @Test
//    public void testBoardDisplay() {
//        BoardBuilder bb = new ClassicBoardBuilder();
//        bb.buildBoard(2);
//        Board board = bb.getBoard();
//        board.displayBoard();
//    }

    @Test
    public void testPlayerSetup() {
        for (int player_count : new int[]{2,3,4,6}) {
            BoardBuilder bb = new ClassicBoardBuilder();
            bb.buildBoard(player_count);
            Board board = bb.getBoard();
            board.displayBoard();
        }
    }

    @Test
    public void testMove() {
        BoardBuilder bb = new ClassicBoardBuilder();
        bb.buildBoard(6);
        Board board = bb.getBoard();
        board.displayBoard();
        board.updateBoard(4, 6, 4, 8);
        board.displayBoard();
        board.updateBoard(3, 9, 4, 10);
        board.displayBoard();
    }
}
