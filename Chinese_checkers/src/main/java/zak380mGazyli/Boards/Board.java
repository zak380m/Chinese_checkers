package zak380mGazyli.Boards;

import zak380mGazyli.Cells.ClassicBoardCell;

public interface Board {
    ClassicBoardCell[][] getBoard();
    void updateBoard(int startX, int startY, int endX, int endY);
    void displayBoard();
}

