package zak380mGazyli.GameModes;

import zak380mGazyli.Boards.*;

public class DummyGameMode implements GameMode {

    @Override
    public boolean validateMove(int[] startPos, int[] endPos, Board board) {
        return true;
    }

    @Override
    public void processMove(int[] startPos, int[] endPos, Board board) {
        board.updateBoard(startPos, endPos);
    }
}

