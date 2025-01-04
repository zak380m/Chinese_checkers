package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.Board;

public interface Gamemode {
    public boolean setNumberOfPlayers(int numberOfPlayers);
    public boolean validateMove(int startX, int startY, int endX, int endY, Board board);
    public void processMove(int startX, int startY, int endX, int endY, Board board);
    public void processPass();
    public boolean isPass();
    public boolean isTerminal();
    public boolean isDraw();
    public int getTurn();
}
