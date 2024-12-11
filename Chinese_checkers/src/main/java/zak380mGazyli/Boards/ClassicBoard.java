package zak380mGazyli.Boards;

import zak380mGazyli.Cells.Cell;
import zak380mGazyli.Misc.Color;

public class ClassicBoard implements Board {
    private final Cell[][] cells;
    private final String cell_sign = "0";
    private final int x_size = 10; // Placeholder board size
    private final int y_size = 10; // Placeholder board size

    public ClassicBoard() {
        this.cells = new Cell[x_size][y_size];
        for (int i = 0; i < x_size; i++) { // Placeholder board creation (currently a square)
            for (int j = 0; j < y_size; j++) {
                this.cells[i][j] = new Cell();
            }
        }
    }

    @Override
    public Cell[][] getBoard() {
        return cells;
    }

    @Override
    public void updateBoard(int startX, int startY, int endX, int endY) {
        // Implementation for swapping two cells
    }

    @Override
    public void displayBoard() {
        // Displaying the board in console for debugging
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                System.out.print(Color.getEscapeSequence(cells[i][j].getColor()) + cell_sign + Color.getEscapeSequence("RESET") + " ");
            }
            System.out.print("\n");
        }
    }
}

