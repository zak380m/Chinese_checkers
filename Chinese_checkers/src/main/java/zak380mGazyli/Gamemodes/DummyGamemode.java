package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.*;

public class DummyGamemode implements Gamemode {

    @Override
    public boolean validateMove(int[] startPos, int[] endPos, Board board) {
        return true;
    }

    @Override
    public void processMove(int[] startPos, int[] endPos, Board board) {
        board.updateBoard(startPos, endPos);
    }
}
