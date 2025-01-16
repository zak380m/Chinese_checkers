package zak380mGazyli.Displays.GUI.Popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import zak380mGazyli.Displays.GUI.GUIDisplay;
import zak380mGazyli.Messages.Command;

public class GameWonPopup {
    private final int position;

    public GameWonPopup(int position) {
        this.position = position;
    }

    public void show() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Chinese Checkers - Game Finished!");

        Label infoLabel = new Label("You've finished: " + position + "!\nWell done!\n\nDo you wish to spectate or leave?");
        infoLabel.setTextAlignment(TextAlignment.CENTER);

        Button spectateButton = new Button("Spectate");
        spectateButton.setOnAction(e -> {
            popupStage.close();
        });

        Button leaveButton = new Button("Leave");
        leaveButton.setOnAction(e -> {
            GUIDisplay.setCurrentCommand(new Command("quit"));
            popupStage.close();
        });

        HBox buttonBox = new HBox(10, spectateButton, leaveButton);

        VBox gameFinishedBox = new VBox(10, infoLabel, buttonBox);
        gameFinishedBox.setPadding(new Insets(10));
        gameFinishedBox.setAlignment(Pos.CENTER);

        Scene gameFinishedScene = new Scene(gameFinishedBox);
        popupStage.setScene(gameFinishedScene);
        popupStage.show();
    }
}
