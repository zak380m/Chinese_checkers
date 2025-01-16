package zak380mGazyli.Displays.GUI.BoardPane;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import zak380mGazyli.Displays.GUIDisplay;
import zak380mGazyli.Displays.GUI.Misc.ColorWrapper;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Misc.Cell;
import java.util.Objects;

/**
 * The BoardPane class represents the game board in the GUI.
 * It extends Pane and contains a GridPane to display the board cells.
 */
public class BoardPane extends Pane {
    private static final int height = 500;
    private static final int width = 500;
    private static final BoardCircle[] selectedCells = new BoardCircle[2];

    private static final GridPane gridPane = new GridPane();

    /**
     * Constructs a new BoardPane.
     * Initializes the preferred size and adds the GridPane to the Pane.
     */
    public BoardPane() {
        setPrefSize(width, height);
        gridPane.setPrefSize(width, height);
        GridPane.setHalignment(gridPane, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(gridPane, javafx.geometry.VPos.CENTER);
        getChildren().add(gridPane);
    }

    /**
     * Displays the game board.
     * Clears the selected cells and the GridPane, then adds the cells from the board array.
     *
     * @param board The 2D array representing the game board.
     */
    public static void displayBoard(Cell[][] board) {
        clearSelectedCells();
        gridPane.getChildren().clear();

        double circle_size = (double) Math.max(width, height) / Math.max(board.length, board[0].length) / 2;

        for (int i = 0; i < board.length; i++) {
            gridPane.getRowConstraints().add(new RowConstraints((double) height / board.length));
            for (int j = 0; j < board[i].length; j++) {
                gridPane.getColumnConstraints().add(new ColumnConstraints((double) width / board[i].length));
                if (!Objects.equals(board[i][j].getSymbol(), " ")) {
                    gridPane.add(new BoardCircle(circle_size, ColorWrapper.getJavaFXColor(board[i][j].getColor()), i, j), j, i);
                }
            }
        }
    }

    /**
     * Selects a cell on the board.
     *
     * @param cell The BoardCircle representing the selected cell.
     * @param index The index in the selectedCells array to store the selected cell.
     */
    public static void selectedCell(BoardCircle cell, int index) {
        selectedCells[index] = cell;
    }

    /**
     * Unselects a cell on the board.
     *
     * @param index The index in the selectedCells array to clear.
     */
    public static void unselectedCell(int index) {
        selectedCells[index] = null;
    }

    /**
     * Gets the number of selected cells.
     *
     * @return The number of selected cells.
     */
    public static int getSelectedCells() {
        int count = 0;
        for (BoardCircle cell : selectedCells) {
            if (cell != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Confirms the move if two cells are selected.
     * Sends a move command to the GUIDisplay and clears the selected cells.
     */
    public static void confirmMove() {
        if (getSelectedCells() == 2) {
            GUIDisplay.setCurrentCommand(new Command("move", new int[] { selectedCells[0].getCol(), selectedCells[0].getRow(), selectedCells[1].getCol(), selectedCells[1].getRow() }));

            clearSelectedCells();
        }
    }

    /**
     * Clears the selected cells.
     * Toggles the selection state of each selected cell.
     */
    private static void clearSelectedCells() {
        for (int i = 1; i >= 0; i--) {
            if (selectedCells[i] != null) {
                selectedCells[i].toggleSelection();
            }
        }
    }
}
