package zak380mGazyli;

import java.io.*;
import java.net.*;

import zak380mGazyli.Displays.*;

public class Client {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Display display = new CLIDisplay();  
    private boolean isConnected = true;

    public static void main(String[] args) throws IOException {
        new Client().startClient();
    }

    public void startClient() throws IOException {
        try {
            socket = new Socket("localhost", 5555);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to server.");

            new Thread(() -> {
                try {
                    while (true) {
                        String currentGameState = (String) in.readObject();
                        display.displayInterface(currentGameState);
                    }
                } catch (EOFException e) {
                    System.out.println("Connection to server lost.");
                    quit();
                } catch (IOException | ClassNotFoundException e) {
                    if (isConnected) { 
                        System.out.println("Connection to server lost due to error: " + e.getMessage());
                        quit();
                    }
                }
            }).start();

            sendCommands();
        } catch (IOException e) {
            System.out.println("Failed to connect to the server: " + e.getMessage());
        }
    }

    private void sendCommands() {
        while (true) {
            try {
                out.writeObject(display.getCommands());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void quit() {
        isConnected = false;
        System.out.println("Client is quitting...");
        display.quit();

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing socket: " + e.getMessage());
        }

        System.exit(0);
    }
}

