package zak380mGazyli.Displays.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import zak380mGazyli.Displays.GUI.Buttons.PassButton;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Misc.Cell;

public class GUIApp extends Application {
    private static Stage primaryStage = null;
    private static final Label msgBox = new Label("msgBox");

    @Override
    public void start(Stage primaryStage) {
        Rectangle rect = new Rectangle(500, 500);
        rect.setFill(javafx.scene.paint.Color.GREY);

        Button moveButton = new Button("Move");
        Button passButton = new PassButton();
        Button quitButton = new Button("Quit");

        VBox buttonBox = new VBox(moveButton, passButton, quitButton);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));

        HBox hBox = new HBox(rect, buttonBox);
        hBox.setSpacing(10);



        VBox vBox = new VBox(hBox, msgBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        Scene scene = new Scene(vBox);

        primaryStage.setTitle("Chinese Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> GUIDisplay.setCurrentCommand(new Command("quit")));

        GUIApp.primaryStage = primaryStage;
    }

    public static void handleSetUpGameMode() {
        Platform.runLater(() -> {
            Stage popupStage = new Stage();
            popupStage.setTitle("Gamemode Configuration");

            TextField gamemodeName = new TextField();
            gamemodeName.setPromptText("Gamemode Name");
            TextField playerNumber = new TextField();
            playerNumber.setPromptText("Number of Players");

            Button startButton = new Button("Start");
            startButton.setOnAction(e -> {
                try {
                    int numberOfPlayers = Integer.parseInt(playerNumber.getText());
                    if (numberOfPlayers <= 0) {
                        throw new IllegalArgumentException("Invalid number of players.");
                    }
                    GUIDisplay.setCurrentCommand(new Command("setUpGamemode", new int[] { numberOfPlayers, 0 }, gamemodeName.getText()));
                    popupStage.close();
                } catch (Exception ex) {
                    displayError("Invalid input: " + ex.getMessage());
                }
            });

            VBox vboxConfig = new VBox(10, gamemodeName, playerNumber, startButton);
            vboxConfig.setPadding(new Insets(10));
            vboxConfig.setAlignment(Pos.CENTER);

            Scene popupScene = new Scene(vboxConfig);
            popupStage.setScene(popupScene);
            popupStage.show();
        });
    }

    public static void handleDisplayBoard(Cell[][] board) {
//        Platform.runLater(() -> {
//
//        });
    }

    public static void handleRemaningTurns(int turns) {
        Platform.runLater(() -> primaryStage.setTitle("Chinese Checkers - Turns until your move: " + turns));
    }

    public static void displayError(String message) {
        Platform.runLater(() -> msgBox.setText(message));
    }

    public static void displayMessage(String message) {
        Platform.runLater(() -> msgBox.setText(message));
    }
}
