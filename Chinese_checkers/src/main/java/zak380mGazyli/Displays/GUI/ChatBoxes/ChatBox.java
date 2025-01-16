package zak380mGazyli.Displays.GUI.ChatBoxes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import zak380mGazyli.Displays.GUI.GUIDisplay;
import zak380mGazyli.Messages.Command;

public class ChatBox extends VBox {
    private final TextFlow chatLog;
    private final TextField inputField;

    public ChatBox() {
        chatLog = new TextFlow();
        chatLog.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(chatLog);
        setVgrow(scrollPane, Priority.ALWAYS);

        inputField = new TextField();
        inputField.setPromptText("Enter your message here...");
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage());

        inputField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });

        setSpacing(10);

        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setAlignment(Pos.CENTER);

        getChildren().addAll(scrollPane, inputBox);
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            GUIDisplay.setCurrentCommand(new Command("message", new int[] { -1 }, message));
            displayMessage("You: " + message);
            inputField.clear();
        }
    }

    public void displayError(String message) {
        Text errorText = new Text("Error: " + message + "\n");
        errorText.setStyle("-fx-fill: red;");
        chatLog.getChildren().add(errorText);
    }

    public void displayMessage(String message) {
        Text messageText = new Text(message + "\n");
        messageText.setStyle("-fx-fill: black;");
        chatLog.getChildren().add(messageText);
    }
}