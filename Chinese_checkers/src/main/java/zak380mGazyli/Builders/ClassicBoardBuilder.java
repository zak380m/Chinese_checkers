package zak380mGazyli.Builders;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Boards.ClassicBoard;

public class ClassicBoardBuilder implements BoardBuilder {
    private final Board board;

    public ClassicBoardBuilder() {
        this.board = new ClassicBoard();
    }

    @Override
    public void buildBoard(int players) {
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
