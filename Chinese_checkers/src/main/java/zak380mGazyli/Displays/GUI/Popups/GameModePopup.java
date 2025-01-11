package zak380mGazyli.Displays.GUI.Popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import zak380mGazyli.Displays.GUI.GUIApp;
import zak380mGazyli.Displays.GUI.GUIDisplay;
import zak380mGazyli.Messages.Command;

public class GameModePopup {
    public void show() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Gamemode Configuration");

        TextField gamemodeName = new TextField();
        gamemodeName.setPromptText("Gamemode Name");
        gamemodeName.setText("BasicGame");

        TextField playerNumber = new TextField();
        playerNumber.setPromptText("Number of Players");
        playerNumber.setText("2");

        gamemodeName.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleStartButtonAction(popupStage, gamemodeName, playerNumber);
            }
        });

        playerNumber.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleStartButtonAction(popupStage, gamemodeName, playerNumber);
            }
        });

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> handleStartButtonAction(popupStage, gamemodeName, playerNumber));

        VBox vboxConfig = new VBox(10, gamemodeName, playerNumber, startButton);
        vboxConfig.setPadding(new Insets(10));
        vboxConfig.setAlignment(Pos.CENTER);

        Scene popupScene = new Scene(vboxConfig);
        popupStage.setScene(popupScene);
        popupStage.show();
        popupStage.toFront();
    }

    private void handleStartButtonAction(Stage popupStage, TextField gamemodeName, TextField playerNumber) {
    try {
        int numberOfPlayers = Integer.parseInt(playerNumber.getText());
        if (numberOfPlayers <= 0) {
            throw new IllegalArgumentException("Invalid number of players.");
        }
        GUIDisplay.setCurrentCommand(new Command("setUpGamemode", new int[] { numberOfPlayers, 0 }, gamemodeName.getText()));
        popupStage.close();
    } catch (Exception ex) {
        GUIApp.displayError("Invalid input: " + ex.getMessage());
    }
}
}