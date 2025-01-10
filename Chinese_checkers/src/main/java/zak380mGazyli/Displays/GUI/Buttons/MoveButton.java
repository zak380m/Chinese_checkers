package zak380mGazyli.Displays.GUI.Buttons;

import javafx.scene.control.Button;
import zak380mGazyli.Displays.GUI.BoardPane.BoardPane;

public class MoveButton extends Button {
    public MoveButton() {
        super("Move");
        setOnAction(e -> BoardPane.confirmMove());
    }
}
