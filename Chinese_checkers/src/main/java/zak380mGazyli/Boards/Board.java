package zak380mGazyli.Boards;

import zak380mGazyli.Misc.Cell;

public interface Board {
    Cell[][] getBoard();
    void updateBoard(int startX, int startY, int endX, int endY);
    void displayBoard();
}

