package zak380mGazyli.Displays.GUI.BoardPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class BoardCircle extends Circle {
    private final int row;
    private final int col;
    private boolean selected = false;
    private int position;

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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
