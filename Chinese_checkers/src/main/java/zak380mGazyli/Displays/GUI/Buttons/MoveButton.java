package zak380mGazyli.Displays.GUI.Buttons;

import javafx.scene.control.Button;
import zak380mGazyli.Displays.GUI.BoardPane.BoardPane;

/**
 * The MoveButton class represents a button that, when clicked, confirms a move on the game board.
 */
public class MoveButton extends Button {
    /**
     * Constructs a new MoveButton with the label "Move".
     * Sets the action to be performed when the button is clicked.
     */
    public MoveButton() {
        super("Move");
        setOnAction(e -> BoardPane.confirmMove());
    }
}