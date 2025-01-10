package zak380mGazyli.Displays.GUI.Buttons;

import javafx.application.Platform;
import javafx.scene.control.Button;
import zak380mGazyli.Displays.GUI.GUIDisplay;
import zak380mGazyli.Messages.Command;

public class QuitButton extends Button {
    public QuitButton() {
        super("Quit");
        setOnAction(e -> {
            GUIDisplay.setCurrentCommand(new Command("quit"));
            Platform.exit();
        });
    }
}
