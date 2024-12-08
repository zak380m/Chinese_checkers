package zak380mGazyli.Displays;

import zak380mGazyli.Cells.Cell;

public class CLIDisplay implements Display {
    
    @Override
    public void displayBoard(Cell[][] board) {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                System.out.print(cell.getColor() + "O ");
            }
            System.out.println();
        }
    }
}

