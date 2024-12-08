package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.*;

public interface GameMode {
    boolean validateMove(int[] startPos, int[] endPos, Board board);  
    void processMove(int[] startPos, int[] endPos, Board board);  
}

