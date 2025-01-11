package zak380mGazyli.Boards;

import zak380mGazyli.Misc.Cell;

public interface Board {
    Cell[][] getBoard();
    int[][] getStartArea(int area_num);
    int[][] getNeighbours();
    void updateBoard(int startX, int startY, int endX, int endY);
    void displayBoard();
}

