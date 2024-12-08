package zak380mGazyli.Boards;

public class ClassicBoard implements Board {
    @Override
    public Board getBoard() {
        return new ClassicBoard();
    }
}
