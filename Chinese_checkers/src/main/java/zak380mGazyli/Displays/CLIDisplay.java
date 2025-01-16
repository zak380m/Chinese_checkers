package zak380mGazyli.Displays;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import zak380mGazyli.Messages.Command;
import zak380mGazyli.Messages.ErrorMessage;
import zak380mGazyli.Messages.Message;
import zak380mGazyli.Misc.Cell;
import zak380mGazyli.Misc.Color;
import zak380mGazyli.Misc.GameState;
import zak380mGazyli.Misc.SetUp;

/**
 * The CLIDisplay class implements the Display interface and provides methods to display the game interface,
 * handle commands, and manage the game state using a command-line interface.
 */
public class CLIDisplay implements Display {

    private Scanner scanner;
    private Gson gson;
    Map<String, CommandHandler> commandHandlers;
    private Map<String, String> commandDescriptions;

    /**
     * Constructs a new CLIDisplay instance.
     * Initializes the scanner, Gson object, and command handlers.
     */
    public CLIDisplay() {
        scanner = new Scanner(System.in);
        gson = new Gson();
        initializeCommands();
    }

    /**
     * Interface for handling commands.
     */
    private interface CommandHandler {
        String handle();
    }

    /**
     * Initializes the command handlers and their descriptions.
     */
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

        commandHandlers.put("message", this::handleMessageCommand);
        commandDescriptions.put("message", "Send message to player/s.");

        commandHandlers.put("help", this::handleHelpCommand);
        commandDescriptions.put("help", "List and explain all available commands.");

        commandHandlers.put("pass", this::handlePassCommand);
        commandDescriptions.put("pass", "Pass your turn.");
    }

    /**
     * Displays the game interface based on the provided JSON response.
     * Parses the JSON response and updates the game state accordingly.
     *
     * @param jsonResponse The JSON response containing game state information.
     */
    @Override
    public void displayInterface(String jsonResponse) {
        clearTerminal();
        try {
            if (jsonResponse.contains("create")) {
                System.out.println("Type: setupgamemode");
            } else if (jsonResponse.contains("boardState")) {
                GameState gameState = gson.fromJson(jsonResponse, GameState.class);
                displayBoard(gameState.getBoardState());
                String moveorpass = gameState.getIsPass() ? " pass" : " moved";
                System.out.println("Player " + gameState.getWhoMoved() + " just" + moveorpass + ".");
            }
            else if (jsonResponse.contains("error")) {
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

    /**
     * Retrieves the current command as a JSON string.
     * Waits for a command to be entered if none is available.
     *
     * @return The current command as a JSON string.
     */
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

    /**
     * Handles the "move" command.
     * Prompts the user to enter the start and end coordinates for the move.
     *
     * @return The move command as a JSON string.
     */
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

    /**
     * Handles the "pass" command.
     *
     * @return The pass command as a JSON string.
     */
    private String handlePassCommand() {
        Command passCommand = new Command("pass");
        return gson.toJson(passCommand);
    }

    /**
     * Handles the "display" command.
     *
     * @return The display command as a JSON string.
     */
    private String handleDisplayCommand() {
        Command displayCommand = new Command("display");
        return gson.toJson(displayCommand);
    }

    /**
     * Handles the "quit" command.
     * Prints a quitting message.
     *
     * @return The quit command as a JSON string.
     */
    private String handleQuitCommand() {
        Command quitCommand = new Command("quit");
        System.out.println("Quitting the game...");
        return gson.toJson(quitCommand);
    }

    /**
     * Handles the "setupgamemode" command.
     * Prompts the user to enter the game mode setup details.
     *
     * @return The setup game mode command as a JSON string.
     */
    private String handleSetUpGamemodeCommand() {
        try {
            SetUp setUp = new SetUp();
            String command;
            System.out.println("Do you want to create a new game? (yes/no)");
            command = scanner.nextLine();
            if(command.toLowerCase().equals("yes")) {
                setUp.setCreate(true);
                System.out.println("Enter gamemode name:");
                setUp.setGamemode(scanner.nextLine());
                System.out.println("Enter number of players:");
                setUp.setPlayerCount(scanner.nextInt());
                scanner.nextLine();
                System.out.println("Enter password:");
                setUp.setPassword(scanner.nextLine());
            } else if(command.toLowerCase().equals("no")) {
                setUp.setCreate(false);
                System.out.println("Enter gamemode name:");
                setUp.setGamemode(scanner.nextLine());
                System.out.println("Enter password:");
                setUp.setPassword(scanner.nextLine());
            } else {
                return gson.toJson(new Command("Invalid command."));
            }
            return gson.toJson(setUp);
        } catch (Exception e) {
            scanner.nextLine();
            return gson.toJson(new Command("Invalid command."));
        }
    }

    /**
     * Handles the "help" command.
     * Lists and explains all available commands.
     *
     * @return The help command as a JSON string.
     */
    private String handleHelpCommand() {
        System.out.println("Available commands:");
        for (String command : commandDescriptions.keySet()) {
            System.out.println(command + ": " + commandDescriptions.get(command));
        }
        return getCommands();
    }

    /**
     * Handles the "message" command.
     * Prompts the user to enter a message and the player number to send it to.
     *
     * @return The message command as a JSON string.
     */
    private String handleMessageCommand() {
        try {
            System.out.println("Enter message, playerNumber(or -1 for all):");
            String message = scanner.nextLine();
            int playerNumber = scanner.nextInt();
            scanner.nextLine();
            Command setMessageCommand = new Command("message", new int[] { playerNumber }, message);
            return gson.toJson(setMessageCommand);
        } catch (Exception e) {
            scanner.nextLine();
            return gson.toJson(new Command("Invalid command."));
        }
    }

    /**
     * Closes the scanner.
     */
    @Override
    public void quit() {
        scanner.close();
    }

    /**
     * Displays the game board.
     *
     * @param board The game board represented as a 2D array of Cell objects.
     */
    private void displayBoard(Cell[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(Color.getEscapeSequence(board[i][j].getColor()) + board[i][j].getSymbol());
            }
            System.out.print("\n");
        }
    }

    /**
     * Clears the terminal screen.
     */
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
