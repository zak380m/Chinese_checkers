package zak380mGazyli.Displays.GUI.Buttons;

import javafx.application.Platform;
import javafx.scene.control.Button;
import zak380mGazyli.Displays.GUIDisplay;
import zak380mGazyli.Messages.Command;

/**
 * The QuitButton class represents a button that, when clicked, sends a "quit" command and exits the application.
 */
public class QuitButton extends Button {
    /**
     * Constructs a new QuitButton with the label "Quit".
     * Sets the action to be performed when the button is clicked.
     */
    public QuitButton() {
        super("Quit");
        setOnAction(e -> {
            GUIDisplay.setCurrentCommand(new Command("quit"));
            Platform.exit();
        });
    }
}