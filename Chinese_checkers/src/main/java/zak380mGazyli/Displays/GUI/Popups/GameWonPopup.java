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
import zak380mGazyli.Displays.GUIDisplay;
import zak380mGazyli.Messages.Command;

/**
 * The GameWonPopup class represents a popup window that is displayed when the game is won.
 * It shows the player's finishing position and provides options to spectate or leave the game.
 */
public class GameWonPopup {
    private final int position;

    /**
     * Constructs a new GameWonPopup.
     *
     * @param position The finishing position of the player.
     */
    public GameWonPopup(int position) {
        this.position = position;
    }

    /**
     * Displays the popup window with the game won message.
     * Provides buttons for the player to choose to spectate or leave the game.
     */
    public void show() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Chinese Checkers - Game Finished!");

        Label infoLabel = new Label("You've finished: " + position + "!\nWell done!\n\nDo you wish to spectate or leave?");
        infoLabel.setTextAlignment(TextAlignment.CENTER);

        Button spectateButton = new Button("Spectate");
        spectateButton.setPrefWidth(100);
        spectateButton.setOnAction(e -> {
            popupStage.close();
        });

        Button leaveButton = new Button("Leave");
        leaveButton.setPrefWidth(100);
        leaveButton.setOnAction(e -> {
            GUIDisplay.setCurrentCommand(new Command("quit"));
            popupStage.close();
        });

        VBox gameFinishedBox = new VBox(10, infoLabel, spectateButton, leaveButton);
        gameFinishedBox.setPadding(new Insets(10));
        gameFinishedBox.setAlignment(Pos.CENTER);

        Scene gameFinishedScene = new Scene(gameFinishedBox);
        popupStage.setScene(gameFinishedScene);
        popupStage.show();
    }
}
