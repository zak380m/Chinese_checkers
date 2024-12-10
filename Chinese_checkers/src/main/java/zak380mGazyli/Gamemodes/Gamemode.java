package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.Board;

public interface Gamemode {
    boolean validateMove(int startX, int startY, int endX, int endY, Board board);
    public void processMove(int startX, int startY, int endX, int endY, Board board);
    int getTurn();
}
