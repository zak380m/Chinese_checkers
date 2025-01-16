package zak380mGazyli.Displays.GUI.Buttons;

import javafx.scene.control.Button;
import zak380mGazyli.Displays.GUIDisplay;
import zak380mGazyli.Messages.Command;

/**
 * The PassButton class represents a button that, when clicked, sends a "pass" command.
 */
public class PassButton extends Button {
    /**
     * Constructs a new PassButton with the label "Pass".
     * Sets the action to be performed when the button is clicked.
     */
    public PassButton() {
        super("Pass");
        setOnAction(e -> GUIDisplay.setCurrentCommand(new Command("pass")));
    }
}