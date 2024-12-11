package zak380mGazyli.Displays;

import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import zak380mGazyli.GameState;
import zak380mGazyli.Command;
import zak380mGazyli.Messages.ErrorMessage;
import zak380mGazyli.Messages.Message;

public class CLIDisplay implements Display {

    private Scanner scanner;

    public CLIDisplay() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void displayInterface(String jsonResponse) {
        Gson gson = new Gson();
        try {
            GameState gameState = gson.fromJson(jsonResponse, GameState.class);
            System.out.println("Turns until your move: " + gameState.getTurnsBeforePlayer());
            Message message = gson.fromJson(jsonResponse, Message.class);
            if(message.getMessage() != null) System.out.println("Message: " + message.getMessage());
        } catch (JsonSyntaxException e) {
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(jsonResponse);
            System.out.println("Error: " + errorMessage.getError() + "\n" + e.getMessage());
        }
    }

    @Override
    public String getCommands() {
        Gson gson = new Gson();

        System.out.println("Enter command (move, display, quit):");
        while(true) {
            if(scanner.hasNextLine()) {
                break;
            }
        }
        String command = scanner.nextLine();
        try {
            switch (command) {
                case "move":
                    System.out.println("Enter startX, startY, endX, endY:");
                    int startX = scanner.nextInt();
                    int startY = scanner.nextInt();
                    int endX = scanner.nextInt();
                    int endY = scanner.nextInt();
                    scanner.nextLine(); 
                    Command moveCommand = new Command("move", new int[]{startX, startY, endX, endY});
                    return gson.toJson(moveCommand);
                case "display":
                    Command displayCommand = new Command("display");
                    return gson.toJson(displayCommand);
                case "quit":
                    Command quitCommand = new Command("quit");
                    System.out.println("Quitting the game...");
                    return gson.toJson(quitCommand);
                case "setUpGamemode":
                    Command setUpGamemodeCommand = new Command("setUpGamemode");
                    return gson.toJson(setUpGamemodeCommand);
                default:
                    Command unknownCommand = new Command("Unknown command.");
                    return gson.toJson(unknownCommand);
                }
        } catch (Exception e) {
            Command invalidCommand = new Command("Invalid input.");
            return gson.toJson(invalidCommand);
        }
    }

    @Override
    public void quit() {
        scanner.close();
    }
}

