package zak380mGazyli.PlayersHandling;

import java.io.*;
import java.net.*;

import zak380mGazyli.Server;

public class Listener extends Thread {
    private ServerSocket serverSocket;
    private Server server;

    public Listener(ServerSocket serverSocket, Server server) {
        this.serverSocket = serverSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket playerSocket = serverSocket.accept();
                System.out.println("New player connected.");

                new Thread(() -> handleNewPlayer(playerSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleNewPlayer(Socket playerSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            PrintWriter out = new PrintWriter(playerSocket.getOutputStream(), true);

            // Ask the player whether they want to create or join a game
            out.println("Welcome! Do you want to (1) Create a new game or (2) Join an existing game?");
            String choice = in.readLine();

            if (choice.equals("1")) {
                out.println("Enter the game name:");
                String gameName = in.readLine();
                out.println("Enter the game password:");
                String password = in.readLine();
                out.println("Enter the number of players:");
                int numPlayers = Integer.parseInt(in.readLine());
                out.println("Enter the number of bots:");
                int numBots = Integer.parseInt(in.readLine());

                // Create a new game room
                GameRoom gameRoom = server.createGameRoom(gameName, password, numPlayers, numBots);
                out.println("Game created! Room ID: " + gameRoom.getRoomId());
                gameRoom.addPlayer(playerSocket);
            } else if (choice.equals("2")) {
                out.println("Enter the game name:");
                String gameName = in.readLine();
                out.println("Enter the game password:");
                String password = in.readLine();

                // Attempt to join an existing game room
                GameRoom gameRoom = server.joinGameRoom(gameName, password);
                if (gameRoom != null) {
                    gameRoom.addPlayer(playerSocket);
                    out.println("Joined game room: " + gameRoom.getGameName());
                } else {
                    out.println("Invalid game name or password.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
