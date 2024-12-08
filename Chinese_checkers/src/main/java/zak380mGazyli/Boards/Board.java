package zak380mGazyli.Boards;

import zak380mGazyli.Cells.Cell;

public interface Board {
    Cell[][] getBoard();
    void updateBoard(int StartPos, int EndPos);
    void displayBoard();
}

