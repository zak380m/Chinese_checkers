package zak380mGazyli.Displays.GUI.BoardPane;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import zak380mGazyli.Displays.GUI.GUIDisplay;
import zak380mGazyli.Displays.GUI.Misc.ColorWrapper;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Misc.Cell;
import java.util.Objects;

public class BoardPane extends Pane {
    private static final int height = 500;
    private static final int width = 500;
    private static final BoardCircle[] selectedCells = new BoardCircle[2];

    private static final GridPane gridPane = new GridPane();

    public BoardPane() {
        setPrefSize(width, height);
        gridPane.setPrefSize(width, height);
        GridPane.setHalignment(gridPane, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(gridPane, javafx.geometry.VPos.CENTER);
        getChildren().add(gridPane);
    }

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

    public static void selectedCell(BoardCircle cell, int index) {
        selectedCells[index] = cell;
    }

    public static void unselectedCell(int index) {
        selectedCells[index] = null;
    }

    public static int getSelectedCells() {
        int count = 0;
        for (BoardCircle cell : selectedCells) {
            if (cell != null) {
                count++;
            }
        }
        return count;
    }

    public static void confirmMove() {
        if (getSelectedCells() == 2) {
            GUIDisplay.setCurrentCommand(new Command("move", new int[] { selectedCells[0].getCol(), selectedCells[0].getRow(), selectedCells[1].getCol(), selectedCells[1].getRow() }));

            clearSelectedCells();
        }
    }

    private static void clearSelectedCells() {
        for (int i = 1; i >= 0; i--) {
            if (selectedCells[i] != null) {
                selectedCells[i].toggleSelection();
            }
        }
    }
}
