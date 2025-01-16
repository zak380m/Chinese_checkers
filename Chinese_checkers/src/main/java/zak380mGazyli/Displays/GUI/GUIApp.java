package zak380mGazyli.Displays.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import zak380mGazyli.Displays.GUI.BoardPane.BoardPane;
import zak380mGazyli.Displays.GUI.Buttons.MoveButton;
import zak380mGazyli.Displays.GUI.Buttons.PassButton;
import zak380mGazyli.Displays.GUI.Buttons.QuitButton;
import zak380mGazyli.Displays.GUI.ChatBoxes.ChatBox;
import zak380mGazyli.Displays.GUI.Popups.GameModePopup;
import zak380mGazyli.Displays.GUI.Popups.GameWonPopup;
import zak380mGazyli.Displays.GUIDisplay;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Misc.Cell;

import java.util.List;
import java.util.Map;

/**
 * The GUIApp class is the main entry point for the JavaFX application.
 * It sets up the primary stage and handles various game-related events.
 */
public class GUIApp extends Application {
    private static Stage primaryStage;
    private static ChatBox chatBox;
    private static int playerNumber = -1;
    private static String playerColor = null;
    private static boolean gameWonScreenShown = false;

    /**
     * Starts the JavaFX application by setting up the primary stage.
     *
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        BoardPane boardPane = new BoardPane();

        Button moveButton = new MoveButton();
        Button passButton = new PassButton();
        Button quitButton = new QuitButton();

        HBox buttonBox = new HBox(10, moveButton, passButton, quitButton);

        chatBox = new ChatBox();

        VBox sidePanel = new VBox(10, buttonBox, chatBox);
        VBox.setVgrow(chatBox, Priority.ALWAYS);

        HBox mainBox = new HBox(boardPane, sidePanel);
        mainBox.setSpacing(10);
        mainBox.setPadding(new Insets(10));

        Scene scene = new Scene(mainBox);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Chinese Checkers");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> GUIDisplay.setCurrentCommand(new Command("quit")));

        GUIApp.primaryStage = primaryStage;
    }

    /**
     * Handles the setup of the game mode by displaying a popup window.
     *
     * @param gamemodes The list of available game modes.
     * @param boards The list of available boards.
     */
    public static void handleSetUpGameMode(List<String> gamemodes, List<String> boards) {
        Platform.runLater(() -> {
            GameModePopup popup = new GameModePopup(gamemodes, boards);
            popup.show();
        });
    }

    /**
     * Sets the player attributes such as player number and player color.
     *
     * @param playerNumber The number assigned to the player.
     * @param playerColor The color assigned to the player.
     */
    public static void setPlayerAttributes(int playerNumber, String playerColor) {
        if (GUIApp.playerNumber == -1)
            GUIApp.playerNumber = playerNumber;
        if (GUIApp.playerColor == null)
            GUIApp.playerColor = playerColor;
    }

    /**
     * Gets the color assigned to the player.
     *
     * @return The player's color.
     */
    public static String getPlayerColor() {
        return playerColor;
    }

    /**
     * Displays the game board.
     *
     * @param board The game board represented as a 2D array of Cell objects.
     */
    public static void handleDisplayBoard(Cell[][] board) {
        Platform.runLater(() -> BoardPane.displayBoard(board));
    }

    /**
     * Handles the player's position and displays the game won popup if the player has finished.
     *
     * @param playerPlace A map containing player numbers and their respective positions.
     */
    public static void handlePlayerPosition(Map<Integer, Integer> playerPlace) {
        if (playerPlace.get(playerNumber) != 0 && !gameWonScreenShown) {
            gameWonScreenShown = true;
            Platform.runLater(() -> {
                GameWonPopup popup = new GameWonPopup(playerPlace.get(playerNumber));
                popup.show();
            });
        }
    }

    /**
     * Handles the current player's turn and updates the primary stage title accordingly.
     *
     * @param player The number of the current player.
     */
    public static void handleCurrentPlayer(int player) {
        Platform.runLater(() -> {
            if (primaryStage != null) {
                if (player == playerNumber) {
                    primaryStage.setTitle("Chinese Checkers - " + playerColor + " - your move!");
                    primaryStage.toFront();
                } else {
                    primaryStage.setTitle("Chinese Checkers - Player " + player + "'s turn.");
                }
            }
        });
    }

    /**
     * Displays an error message in the chat box.
     *
     * @param message The error message to be displayed.
     */
    public static void displayError(String message) {
        Platform.runLater(() -> chatBox.displayError(message));
    }

    /**
     * Displays a message in the chat box.
     *
     * @param message The message to be displayed.
     */
    public static void displayMessage(String message) {
        Platform.runLater(() -> {
            String[] messageParts = message.split(" FROM ");
            chatBox.displayMessage(messageParts[1] + ": " + messageParts[0]);
        });
    }

    /**
     * Shows the primary stage.
     */
    public static void show() {
        primaryStage.show();
    }
}
