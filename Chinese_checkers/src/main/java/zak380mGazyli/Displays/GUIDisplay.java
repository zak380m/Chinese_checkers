package zak380mGazyli.Displays;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.application.Application;
import zak380mGazyli.Displays.GUI.GUIApp;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Messages.ErrorMessage;
import zak380mGazyli.Messages.Message;
import zak380mGazyli.Misc.GameState;
import zak380mGazyli.Misc.SetUp;

/**
 * The GUIDisplay class implements the Display interface and provides methods to display the game interface,
 * handle commands, and manage the game state using JavaFX.
 */
public class GUIDisplay implements Display {
    private final Gson gson;
    public static volatile Command currentCommand;
    private static boolean javafxStarted = false;

    /**
     * Constructs a new GUIDisplay instance.
     * Initializes the Gson object and starts the JavaFX application if it hasn't been started yet.
     */
    public GUIDisplay() {
        gson = new Gson();
        if (!javafxStarted) {
            javafxStarted = true;
            new Thread(() -> Application.launch(GUIApp.class)).start();
        }
    }

    /**
     * Displays the game interface based on the provided JSON response.
     * Parses the JSON response and updates the game state accordingly.
     *
     * @param jsonResponse The JSON response containing game state information.
     */
    @Override
    public void displayInterface(String jsonResponse) {
        try {
            if (jsonResponse.contains("create")) {
                SetUp setUp = gson.fromJson(jsonResponse, SetUp.class);

                GUIApp.handleSetUpGameMode(setUp.getGamemodes(), setUp.getBoards());
            } else if (jsonResponse.contains("boardState")) {
                GameState gameState = gson.fromJson(jsonResponse, GameState.class);

                GUIApp.setPlayerAttributes(gameState.getPlayerNumber(), gameState.getPlayerColor());
                GUIApp.setGameID(gameState.getGameID());
                GUIApp.handlePlayerPosition(gameState.getPlayerPlace());
                GUIApp.handleCurrentPlayer(gameState.getCurrentPlayerTurn());
                GUIApp.handleDisplayBoard(gameState.getBoardState());

                String moveorpass = gameState.getIsPass() ? " pass" : " moved";
                System.out.println("Player " + gameState.getWhoMoved() + " just" + moveorpass + ".");
            }
            else if (jsonResponse.contains("error")) {
                ErrorMessage errorMessage = gson.fromJson(jsonResponse, ErrorMessage.class);

                GUIApp.displayError(errorMessage.getError());

                System.out.println("Error: " + errorMessage.getError());
            }
            else if (jsonResponse.contains("message")) {
                Message message = gson.fromJson(jsonResponse, Message.class);
                if (message.getMessage() != null) {
                    GUIApp.displayMessage(message.getMessage());

                    System.out.println("Message: " + message.getMessage());
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Failed to parse the JSON response: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the current command as a JSON string.
     * Waits for a command to be set if none is available.
     *
     * @return The current command as a JSON string.
     */
    @Override
    public String getCommands() {
        while (currentCommand == null) {
            Thread.onSpinWait();
        }
        if (currentCommand != null) {
            String command = gson.toJson(currentCommand);
            currentCommand = null;
            return command;
        } else {
            return gson.toJson(new Command("Unknown command."));
        }
    }

    /**
     * Quits the GUI and prints a quitting message.
     */
    @Override
    public void quit() {
        System.out.println("GUI is quitting...");
    }

    /**
     * Sets the current command to be executed.
     *
     * @param command The command to be set.
     */
    public static void setCurrentCommand(Command command) {
        currentCommand = command;
    }
}
