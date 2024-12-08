package zak380mGazyli.Builders.BoardBuilders;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Boards.ClassicBoard;

public class ClassicBoardBuilder implements BoardBuilder {
    private Board board;

    @Override
    public void buildBoard(int players) {
        this.board = new ClassicBoard();
        switch (players) {
            case 2:
                // Logic for creating boards for different player amounts
                break;
            case 3:
                break;
            case 4:
                break;
            case 6:
                break;
            default:
        }

    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}
