package zak380mGazyli;

import org.junit.jupiter.api.Test;
import zak380mGazyli.Boards.Board;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.BoardBuilders.ClassicBoardBuilder;
import zak380mGazyli.Misc.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassicBoardTest {
    @Test
    public void testBoardBuilding() {
        for (int player_count : new int[]{2,3,4,6}) {
            BoardBuilder bb = new ClassicBoardBuilder();
            bb.buildBoard(player_count);
            Board board = bb.getBoard();
            board.displayBoard();

            switch (player_count) {
                case 2:
                    for (int[] cell : board.getStartArea(1)) {
                        assertEquals(Color.RED, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(4)) {
                        assertEquals(Color.BLUE, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    break;
                case 3:
                    for (int[] cell : board.getStartArea(1)) {
                        assertEquals(Color.RED, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(3)) {
                        assertEquals(Color.BLUE, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(5)) {
                        assertEquals(Color.GREEN, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    break;
                case 4:
                    for (int[] cell : board.getStartArea(2)) {
                        assertEquals(Color.RED, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(3)) {
                        assertEquals(Color.YELLOW, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(5)) {
                        assertEquals(Color.BLUE, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(6)) {
                        assertEquals(Color.GREEN, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    break;
                case 6:
                    for (int[] cell : board.getStartArea(1)) {
                        assertEquals(Color.RED, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(2)) {
                        assertEquals(Color.MAGENTA, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(3)) {
                        assertEquals(Color.YELLOW, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(4)) {
                        assertEquals(Color.BLUE, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(5)) {
                        assertEquals(Color.GREEN, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    for (int[] cell : board.getStartArea(6)) {
                        assertEquals(Color.CYAN, board.getBoard()[cell[0]][cell[1]].getColor());
                    }
                    break;
            }
        }
    }

    @Test
    public void testUpdateBoard() {
        BoardBuilder bb = new ClassicBoardBuilder();
        bb.buildBoard(6);
        Board board = bb.getBoard();
        board.displayBoard();

        // Move a piece from (3,9) to (4,8)

        assertEquals(Color.RED, board.getBoard()[3][9].getColor());
        assertEquals(Color.WHITE, board.getBoard()[4][8].getColor());

        board.updateBoard(9, 3, 8, 4);
        board.displayBoard();

        assertEquals(Color.WHITE, board.getBoard()[3][9].getColor());
        assertEquals(Color.RED, board.getBoard()[4][8].getColor());


        // Try an invalid move

        board.updateBoard(8, 4, 8, 5);
        board.displayBoard();

        assertEquals(Color.RED, board.getBoard()[4][8].getColor());
    }
}
