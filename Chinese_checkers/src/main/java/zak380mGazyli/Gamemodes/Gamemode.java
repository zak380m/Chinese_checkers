package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.Board;

public interface Gamemode {
    public boolean validateMove(int[] startPos, int[] endPos, Board board);
    public void processMove(int[] startPos, int[] endPos, Board board);
}
