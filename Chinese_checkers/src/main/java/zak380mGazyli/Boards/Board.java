package zak380mGazyli.Boards;

import zak380mGazyli.Misc.Cell;

/**
 * The Board interface represents a game board with methods to manipulate and display the board.
 */
public interface Board {
    /**
     * Gets the current state of the board.
     *
     * @return A 2D array of Cell objects representing the board.
     */
    Cell[][] getBoard();

    /**
     * Gets the start area of the board for a specific area number.
     *
     * @param area_num The number of the area to get the start area for.
     * @return A 2D array of integers representing the start area coordinates.
     */
    int[][] getStartArea(int area_num);

    /**
     * Gets the neighbours of each cell on the board.
     *
     * @return A 2D array of integers representing the neighbours of each cell.
     */
    int[][] getNeighbours();

    /**
     * Updates the board by moving a piece from the start coordinates to the end coordinates.
     *
     * @param startX The starting X coordinate.
     * @param startY The starting Y coordinate.
     * @param endX The ending X coordinate.
     * @param endY The ending Y coordinate.
     */
    void updateBoard(int startX, int startY, int endX, int endY);

    /**
     * Displays the current state of the board.
     */
    void displayBoard();
}
