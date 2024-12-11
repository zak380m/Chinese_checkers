package zak380mGazyli.Displays;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import zak380mGazyli.GameState;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Messages.ErrorMessage;
import zak380mGazyli.Messages.Message;
import zak380mGazyli.Misc.Cell;
import zak380mGazyli.Misc.Color;

public class CLIDisplay implements Display {

    private Scanner scanner;
    private Gson gson;
    Map<String, CommandHandler> commandHandlers;
    private Map<String, String> commandDescriptions;

    public CLIDisplay() {
        scanner = new Scanner(System.in);
        gson = new Gson();
        initializeCommands();
    }

    private interface CommandHandler {
        String handle();
    }

    private void initializeCommands() {
        commandHandlers = new HashMap<>();
        commandDescriptions = new HashMap<>();

        commandHandlers.put("move", this::handleMoveCommand);
        commandDescriptions.put("move", "Move a piece. Format: 'move', followed by startX, startY, endX, endY in next line.");

        commandHandlers.put("display", this::handleDisplayCommand);
        commandDescriptions.put("display", "Display the current game state.");

        commandHandlers.put("quit", this::handleQuitCommand);
        commandDescriptions.put("quit", "Quit the game.");

        commandHandlers.put("setupgamemode", this::handleSetUpGamemodeCommand);
        commandDescriptions.put("setUpGamemode", "Set up the game mode.");

        commandHandlers.put("help", this::handleHelpCommand);
        commandDescriptions.put("help", "List and explain all available commands.");
    }

    @Override
    public void displayInterface(String jsonResponse) {
        clearTerminal();
        try {
            if (jsonResponse.contains("boardState")) {
                GameState gameState = gson.fromJson(jsonResponse, GameState.class);
                displayBoard(gameState.getBoardState());
                System.out.println("Turns until your move: " + gameState.getTurnsBeforePlayer());
            }
            else if (jsonResponse.contains("errorMessage")) {
                ErrorMessage errorMessage = gson.fromJson(jsonResponse, ErrorMessage.class);
                System.out.println("Error: " + errorMessage.getError());
            }
            else if (jsonResponse.contains("message")) {
                Message message = gson.fromJson(jsonResponse, Message.class);
                if (message.getMessage() != null) {
                    System.out.println("Message: " + message.getMessage());
                }
            }
            System.out.println("Enter command:");
        } catch (JsonSyntaxException e) {
            System.out.println("Failed to parse the JSON response: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    @Override
    public String getCommands() {
        System.out.print("> ");
        while(true) {
            if(scanner.hasNextLine()) {
                break;
            }
        }
        String command = scanner.nextLine().toLowerCase();
        CommandHandler commandHandler = commandHandlers.get(command);
        if (commandHandler != null) {
            return commandHandler.handle();  
        } else {
            return gson.toJson(new Command("Unknown command."));
        }
    }

    private String handleMoveCommand() {
        try {
            System.out.println("Enter startX, startY, endX, endY:");
            int startX = scanner.nextInt();
            int startY = scanner.nextInt();
            int endX = scanner.nextInt();
            int endY = scanner.nextInt();
            scanner.nextLine();  
            Command moveCommand = new Command("move", new int[] { startX, startY, endX, endY });
            return gson.toJson(moveCommand);
        } catch (Exception e) {
            scanner.nextLine();
            return gson.toJson(new Command("Invalid command."));
        }
    }

    private String handleDisplayCommand() {
        Command displayCommand = new Command("display");
        return gson.toJson(displayCommand);
    }

    private String handleQuitCommand() {
        Command quitCommand = new Command("quit");
        System.out.println("Quitting the game...");
        return gson.toJson(quitCommand);
    }

    private String handleSetUpGamemodeCommand() {
        Command setUpGamemodeCommand = new Command("setUpGamemode");
        return gson.toJson(setUpGamemodeCommand);
    }

    private String handleHelpCommand() {
        System.out.println("Available commands:");
        for (String command : commandDescriptions.keySet()) {
            System.out.println(command + ": " + commandDescriptions.get(command));
        }
        return getCommands();
    }

    @Override
    public void quit() {
        scanner.close();
    }

    private void displayBoard(Cell[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(Color.getEscapeSequence(board[i][j].getColor()) + board[i][j].getSymbol());
            }
            System.out.print("\n");
        }
    }

    private void clearTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println("Error clearing terminal: " + ex.getMessage());
        }
    }
}

