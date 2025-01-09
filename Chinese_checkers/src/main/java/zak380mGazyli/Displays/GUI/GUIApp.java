package zak380mGazyli.Displays.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Misc.Cell;

public class GUIApp extends Application {

    private static final VBox vBox = new VBox();

    @Override
    public void start(Stage primaryStage) {
        vBox.setSpacing(10);
        Scene scene = new Scene(vBox, 400, 300);

        primaryStage.setTitle("Chinese Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> GUIDisplay.setCurrentCommand(new Command("quit")));
    }

    public static void handleSetUpGameMode() {
        // Placeholder setup for the game mode
        GUIDisplay.setCurrentCommand(new Command("setUpGamemode", new int[] { 4, 0 }, "DummyGame"));
    }

    public static void displayBoard(Cell[][] board) {
        if (Platform.isFxApplicationThread()) {
            vBox.getChildren().add(new Label("Board:"));
            for (Cell[] row : board) {
                StringBuilder rowString = new StringBuilder();
                for (Cell cell : row) {
                    rowString.append(cell.toString());
                }
                vBox.getChildren().add(new Label(rowString.toString()));
            }
        } else {
            Platform.runLater(() -> displayBoard(board));
        }
    }

    public static void displayError(String message) {
        if (Platform.isFxApplicationThread()) {
            vBox.getChildren().add(new Label(message));
        } else {
            Platform.runLater(() -> vBox.getChildren().add(new Label(message)));
        }
    }

    public static void displayMessage(String message) {
        if (Platform.isFxApplicationThread()) {
            vBox.getChildren().add(new Label(message));
        } else {
            Platform.runLater(() -> vBox.getChildren().add(new Label(message)));
        }
    }
}
