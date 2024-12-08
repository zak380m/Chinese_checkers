package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.*;

public class DummyGamemode implements Gamemode {

    @Override
    public boolean validateMove(int startX, int startY, int endX, int endY, Board board) {
        return true;
    }

    @Override
    public void processMove(int startX, int startY, int endX, int endY, Board board) {
        board.updateBoard(startX, startY, endX, endY);
    }
}
