package zak380mGazyli.Gamemodes;

import java.util.Map;

import zak380mGazyli.Boards.Board;

public interface Gamemode {
    public boolean setNumberOfPlayers(int numberOfPlayers, Board board);
    public boolean validateMove(int startX, int startY, int endX, int endY, Board board);
    public void processMove(int startX, int startY, int endX, int endY, Board board);
    public void processPass();
    public boolean isPass();
    public Map<Integer, Integer> playerPlace();
    public int getTurn();
    public String getName();
    public int getTurnCount();
    public String getPlayerColor(int playerNumber);
}
