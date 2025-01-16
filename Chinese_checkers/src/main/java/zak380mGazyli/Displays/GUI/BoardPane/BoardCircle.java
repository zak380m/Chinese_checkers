package zak380mGazyli.Displays.GUI.BoardPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * The BoardCircle class represents a circle on the game board.
 * It extends Circle and includes additional properties for row, column, and selection state.
 */
public class BoardCircle extends Circle {
    private final int row;
    private final int col;
    private boolean selected = false;
    private int position;

    /**
     * Constructs a new BoardCircle.
     * Initializes the circle with the specified radius, color, row, and column.
     * Sets up the mouse click event handler to toggle selection.
     *
     * @param radius The radius of the circle.
     * @param color The color of the circle.
     * @param row The row position of the circle on the board.
     * @param col The column position of the circle on the board.
     */
    public BoardCircle(double radius, Color color, int row, int col) {
        super(radius);
        if (color == Color.WHITE) {
            setFill(Color.TRANSPARENT);
            setStrokeWidth(radius / 5);
            setStroke(Color.BLACK);
        } else {
            setFill(color);
        }
        this.row = row;
        this.col = col;

        setOnMouseClicked(e -> {
            System.out.println("Clicked on cell (" + row + ", " + col + ")");
            toggleSelection();
        });
    }

    /**
     * Toggles the selection state of the circle.
     * Updates the stroke width and color based on the selection state.
     * Manages the selection state in the BoardPane.
     */
    public void toggleSelection() {
        if (!selected && BoardPane.getSelectedCells() < 2) {
            selected = true;
            setStrokeWidth(getRadius() / 2.5);
            setStrokeType(StrokeType.CENTERED);
            if (BoardPane.getSelectedCells() == 0) {
                setStroke(Color.BLACK);
                BoardPane.selectedCell(this, 0);
                position = 0;
            } else {
                setStroke(Color.DARKGREY);
                BoardPane.selectedCell(this, 1);
                position = 1;
            }
        } else if (selected && !(BoardPane.getSelectedCells() == 2 && position == 0)) {
            selected = false;
            if (getFill() == Color.TRANSPARENT) {
                setStrokeWidth(getRadius() / 5);
                setStroke(Color.BLACK);
                setStrokeType(StrokeType.INSIDE);
            } else {
                setStrokeWidth(0);
            }
            BoardPane.unselectedCell(position);
        }
    }

    /**
     * Gets the row position of the circle on the board.
     *
     * @return The row position of the circle.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column position of the circle on the board.
     *
     * @return The column position of the circle.
     */
    public int getCol() {
        return col;
    }
}
