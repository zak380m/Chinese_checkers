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
import zak380mGazyli.Displays.GUIDisplay;
import zak380mGazyli.Messages.Command;

/**
 * The ChatBox class represents a GUI component for a chat interface.
 * It extends VBox and contains a chat log and an input field for sending messages.
 */
public class ChatBox extends VBox {
    private final TextFlow chatLog;
    private final TextField inputField;

    /**
     * Constructs a new ChatBox.
     * Initializes the chat log, input field, and send button.
     * Sets up the layout and event handling for sending messages.
     */
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

    /**
     * Sends the message entered in the input field.
     * If the message is not empty, it creates a new Command and displays the message in the chat log.
     * Clears the input field after sending the message.
     */
    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            GUIDisplay.setCurrentCommand(new Command("message", new int[] { -1 }, message));
            displayMessage("You: " + message);
            inputField.clear();
        }
    }

    /**
     * Displays an error message in the chat log.
     * The error message is styled in red.
     *
     * @param message The error message to display.
     */
    public void displayError(String message) {
        Text errorText = new Text("Error: " + message + "\n");
        errorText.setStyle("-fx-fill: red;");
        chatLog.getChildren().add(errorText);
    }

    /**
     * Displays a message in the chat log.
     * The message is styled in black.
     *
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        Text messageText = new Text(message + "\n");
        messageText.setStyle("-fx-fill: black;");
        chatLog.getChildren().add(messageText);
    }
}
