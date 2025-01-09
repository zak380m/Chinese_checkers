package zak380mGazyli.Displays.GUI;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.application.Application;
import zak380mGazyli.Displays.Display;
import zak380mGazyli.GameState;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Messages.ErrorMessage;
import zak380mGazyli.Messages.Message;

public class GUIDisplay implements Display {
    private final Gson gson;
    public static volatile Command currentCommand;
    private static boolean javafxStarted = false;


    public GUIDisplay() {
        gson = new Gson();
        if (!javafxStarted) {
            javafxStarted = true;
            new Thread(() -> {
                Application.launch(GUIApp.class);
            }).start();
        }
    }

    @Override
    public void displayInterface(String jsonResponse) {
        try {
            if (jsonResponse.contains("boardState")) {
                GameState gameState = gson.fromJson(jsonResponse, GameState.class);
                GUIApp.displayBoard(gameState.getBoardState());

                String moveorpass = gameState.getIsPass() ? " pass" : " moved";
                System.out.println("Player " + gameState.getWhoMoved() + " just" + moveorpass + ".");
                System.out.println("Turns until your move: " + gameState.getTurnsBeforePlayer());
            }
            else if (jsonResponse.contains("error")) {
                ErrorMessage errorMessage = gson.fromJson(jsonResponse, ErrorMessage.class);
                GUIApp.displayError(errorMessage.getError());

                System.out.println("Error: " + errorMessage.getError());
            }
            else if (jsonResponse.contains("message")) {
                Message message = gson.fromJson(jsonResponse, Message.class);
                if (message.getMessage() != null) {
                    if (message.getMessage().contains("setUpGameMode")) {
                        GUIApp.handleSetUpGameMode();
                    } else {
                        GUIApp.displayMessage(message.getMessage());
                    }

                    System.out.println("Message: " + message.getMessage());
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Failed to parse the JSON response: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

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

    @Override
    public void quit() {
        System.out.println("GUI is quitting...");
    }

    public static void setCurrentCommand(Command command) {
        currentCommand = command;
    }
}
