package zak380mGazyli.Boards;

public class ClassicBoardBuilder implements BoardBuilder {
    private final Board board;

    public ClassicBoardBuilder() {
        this.board = new ClassicBoard();
    }

    @Override
    public void buildBoard(int players) {
        System.out.println("Classic board");
    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}
