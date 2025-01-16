package zak380mGazyli.Builders.BoardBuilders;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Boards.ClassicBoard;
import zak380mGazyli.Misc.Color;

/**
 * The ClassicBoardBuilder class is responsible for building a ClassicBoard
 * with the appropriate configuration based on the number of players.
 */
public class ClassicBoardBuilder implements BoardBuilder {
    private ClassicBoard board;

    /**
     * Builds the board with the specified number of players.
     *
     * @param players The number of players.
     */
    @Override
    public void buildBoard(int players) {
        this.board = new ClassicBoard();
        switch (players) {
            case 2:
                board.colorTriangle2P(1, Color.RED);
                board.colorTriangle2P(4, Color.BLUE);
                break;
            case 3:
                board.colorTriangle(1, Color.RED);
                board.colorTriangle(3, Color.BLUE);
                board.colorTriangle(5, Color.GREEN);
                break;
            case 4:
                board.colorTriangle(2, Color.RED);
                board.colorTriangle(3, Color.YELLOW);
                board.colorTriangle(5, Color.BLUE);
                board.colorTriangle(6, Color.GREEN);
                break;
            case 6:
                board.colorTriangle(1, Color.RED);
                board.colorTriangle(2, Color.MAGENTA);
                board.colorTriangle(3, Color.YELLOW);
                board.colorTriangle(4, Color.BLUE);
                board.colorTriangle(5, Color.GREEN);
                board.colorTriangle(6, Color.CYAN);
                break;
            default:
                board = null;
        }
    }

    /**
     * Gets the built board.
     *
     * @return The built board.
     */
    @Override
    public Board getBoard() {
        return this.board;
    }
}
