package zak380mGazyli.Displays.GUI.Popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import zak380mGazyli.Displays.GUI.GUIApp;
import zak380mGazyli.Displays.GUIDisplay;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Misc.SetUp;

import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The GameModePopup class represents a popup window for setting up the game mode.
 * It allows the user to create or join a game room by selecting a game mode and entering room details.
 */
public class GameModePopup {
    private final List<String> gamemodes;
    private final List<String> boards;
    private Boolean create;

    /**
     * Constructs a new GameModePopup.
     *
     * @param gamemodes The list of available game modes.
     * @param boards The list of available boards.
     */
    public GameModePopup(List<String> gamemodes, List<String> boards) {
        this.gamemodes = gamemodes;
        this.boards = boards;
    }

    /**
     * Displays the popup window for game mode setup.
     * Allows the user to choose between creating a new room or joining an existing one.
     */
    public void show() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Chinese Checkers - Game setup");
        popupStage.setOnCloseRequest(e -> GUIDisplay.setCurrentCommand(new Command("quit")));
        popupStage.toFront();
        popupStage.setResizable(false);

        TextField roomName = new TextField();
        roomName.setPromptText("Room Name");
        roomName.setPrefWidth(100);

        ComboBox<String> gamemodeChooser = new ComboBox<>(observableArrayList(gamemodes));

        gamemodeChooser.setPromptText("Gamemode");
        gamemodeChooser.getSelectionModel().select(1);
        gamemodeChooser.setPrefWidth(100);

        ComboBox<String> boardChooser = new ComboBox<>(observableArrayList(boards));

        boardChooser.setPromptText("Board");
        boardChooser.getSelectionModel().select(0);
        boardChooser.setPrefWidth(100);

        TextField playerNumber = new TextField();
        playerNumber.setPromptText("Number of Players");
        playerNumber.setText("2");
        playerNumber.setPrefWidth(100);

        TextField botNumber = new TextField();
        botNumber.setPromptText("Number of Bots");
        botNumber.setText("0");
        botNumber.setPrefWidth(100);

        roomName.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleStartButtonAction(popupStage, roomName, gamemodeChooser, boardChooser, playerNumber, botNumber);
            }
        });

        playerNumber.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleStartButtonAction(popupStage, roomName, gamemodeChooser, boardChooser, playerNumber,  botNumber);
            }
        });

        GridPane gridConfig = new GridPane();

        gridConfig.setHgap(10);
        gridConfig.setVgap(10);

        gridConfig.add(new Label("Room Name:"), 0, 0);
        gridConfig.add(roomName, 1, 0);

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> handleStartButtonAction(popupStage, roomName, gamemodeChooser, boardChooser, playerNumber, botNumber));
        startButton.setMaxWidth(Double.MAX_VALUE);

        VBox vboxConfig = new VBox(10, gridConfig, startButton);
        vboxConfig.setPadding(new Insets(10));
        vboxConfig.setAlignment(Pos.CENTER);


        Scene configScene = new Scene(vboxConfig);

        Button createButton = new Button("Create Room");
        createButton.setOnAction(e -> {
            setCreate(true);
            gridConfig.add(new Label("Gamemode:"), 0, 1);
            gridConfig.add(gamemodeChooser, 1, 1);
            gridConfig.add(new Label("Board:"), 0, 2);
            gridConfig.add(boardChooser, 1, 2);
            gridConfig.add(new Label("# of players:"), 0, 3);
            gridConfig.add(playerNumber, 1, 3);
            gridConfig.add(new Label("# of bots:"), 0, 4);
            gridConfig.add(botNumber, 1, 4);
            popupStage.setScene(configScene);
        });

        Button joinButton = new Button("Join Room");
        joinButton.setOnAction(e -> {
            setCreate(false);
            popupStage.setScene(configScene);
        });

        Label chooseLabel = new Label("Do you want to create a new room\nor join an existing one?");
        chooseLabel.setTextAlignment(TextAlignment.CENTER);

        VBox chooseBox = new VBox(10, chooseLabel, createButton, joinButton);
        chooseBox.setPadding(new Insets(10));
        chooseBox.setAlignment(Pos.CENTER);

        Scene chooseScene = new Scene(chooseBox);
        popupStage.setScene(chooseScene);
        popupStage.show();
    }

    /**
     * Handles the action when the start button is pressed.
     * Validates the input and sets up the game configuration.
     *
     * @param popupStage The stage of the popup window.
     * @param roomName The text field for the room name.
     * @param gamemodeChooser The combo box for selecting the game mode.
     * @param playerNumber The text field for the number of players.
     */
    private void handleStartButtonAction(Stage popupStage, TextField roomName, ComboBox<String> gamemodeChooser, ComboBox<String> boardChooser, TextField playerNumber, TextField botNumber) {
        try {
            int numberOfPlayers = Integer.parseInt(playerNumber.getText());
            if (numberOfPlayers <= 0) {
                throw new IllegalArgumentException("Invalid number of players.");
            }
            SetUp setUp = new SetUp();
            setUp.setCreate(getCreate());
            setUp.setPassword(roomName.getText());
            if (getCreate()) {
                setUp.setGamemode(gamemodeChooser.getValue());
                setUp.setBoard(boardChooser.getValue());
                setUp.setPlayerCount(numberOfPlayers);
                setUp.setBotCount(Integer.parseInt(botNumber.getText()));
            }
            GUIDisplay.setCurrentCommand(setUp);
            popupStage.close();
            GUIApp.show();
        } catch (Exception ex) {
            GUIApp.displayError("Invalid input: " + ex.getMessage());
        }
    }

    /**
     * Sets the create flag indicating whether to create a new room.
     *
     * @param create The create flag.
     */
    private void setCreate(Boolean create) {
        this.create = create;
    }

    /**
     * Gets the create flag indicating whether to create a new room.
     *
     * @return The create flag.
     */
    public Boolean getCreate() {
        return create;
    }
}
