package zak380mGazyli.Displays.GUI.Buttons;

import javafx.scene.control.Button;
import zak380mGazyli.Displays.GUI.GUIDisplay;
import zak380mGazyli.Messages.Command;

public class PassButton extends Button {
    public PassButton() {
        super("Pass");
        setOnAction(e -> GUIDisplay.setCurrentCommand(new Command("pass")));
    }
}
