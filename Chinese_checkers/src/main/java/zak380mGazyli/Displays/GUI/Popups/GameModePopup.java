package zak380mGazyli.Displays.GUI.Popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import zak380mGazyli.Displays.GUI.GUIApp;
import zak380mGazyli.Displays.GUI.GUIDisplay;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Misc.SetUp;

import java.util.List;

public class GameModePopup {
    private final List<String> gamemodes;
    private final List<String> boards;
    private Boolean create;

    public GameModePopup(List<String> gamemodes, List<String> boards) {
        this.gamemodes = gamemodes;
        this.boards = boards;
    }

    public void show() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Chinese Checkers - Gamemode setup");
        popupStage.setOnCloseRequest(e -> GUIDisplay.setCurrentCommand(new Command("quit")));
        popupStage.toFront();

        ComboBox<String> gamemodeChooser = new ComboBox<>();
        for (String gamemode : gamemodes) {
            gamemodeChooser.getItems().add(gamemode);
        }
        gamemodeChooser.setPromptText("Gamemode:");
        gamemodeChooser.getSelectionModel().select(1);

        TextField roomName = new TextField();
        roomName.setPromptText("Room Name");

        TextField playerNumber = new TextField();
        playerNumber.setPromptText("Number of Players");
        playerNumber.setText("2");

        roomName.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleStartButtonAction(popupStage, roomName, gamemodeChooser, playerNumber);
            }
        });

        playerNumber.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleStartButtonAction(popupStage, roomName, gamemodeChooser, playerNumber);
            }
        });

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> handleStartButtonAction(popupStage, roomName, gamemodeChooser, playerNumber));

        HBox hboxConfig = new HBox(10, roomName, gamemodeChooser);

        VBox vboxConfig = new VBox(10, hboxConfig, startButton);
        vboxConfig.setPadding(new Insets(10));
        vboxConfig.setAlignment(Pos.CENTER);

        Scene configScene = new Scene(vboxConfig);

        Button createButton = new Button("Create Room");
        createButton.setOnAction(e -> {
            setCreate(true);
            hboxConfig.getChildren().add(playerNumber);
            popupStage.setScene(configScene);
        });

        Button joinButton = new Button("Join Room");
        joinButton.setOnAction(e -> {
            setCreate(false);
            popupStage.setScene(configScene);
        });

        Label chooseLabel = new Label("Do you want to create a new room\nor join an existing one?");
        chooseLabel.setTextAlignment(TextAlignment.CENTER);

        VBox chooseBox = new VBox(10,chooseLabel , createButton, joinButton);
        chooseBox.setPadding(new Insets(10));
        chooseBox.setAlignment(Pos.CENTER);

        Scene chooseScene = new Scene(chooseBox);
        popupStage.setScene(chooseScene);
        popupStage.show();
    }

    private void handleStartButtonAction(Stage popupStage, TextField roomName, ComboBox<String> gamemodeChooser, TextField playerNumber) {
        try {
            int numberOfPlayers = Integer.parseInt(playerNumber.getText());
            if (numberOfPlayers <= 0) {
                throw new IllegalArgumentException("Invalid number of players.");
            }
            SetUp setUp = new SetUp();
            setUp.setCreate(getCreate());
            setUp.setPassword(roomName.getText());
            setUp.setGamemode(gamemodeChooser.getValue());
            if (getCreate()) {
                setUp.setPlayerCount(numberOfPlayers);
            }
            GUIDisplay.setCurrentCommand(setUp);
            popupStage.close();
            GUIApp.show();
        } catch (Exception ex) {
            GUIApp.displayError("Invalid input: " + ex.getMessage());
        }
    }

    private void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean getCreate() {
        return create;
    }
}