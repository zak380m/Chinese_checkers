package zak380mGazyli.Builders.BoardBuilders;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Boards.ClassicBoard;
import zak380mGazyli.Misc.Color;

public class ClassicBoardBuilder implements BoardBuilder {
    private ClassicBoard board;

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
                board.colorTriangle(6, Color.GREY);
                break;
            default:
        }

    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}
