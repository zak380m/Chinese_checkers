package zak380mGazyli.PlayersHandling;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;

import zak380mGazyli.Server;
import zak380mGazyli.Builders.GameBuilder;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;
import zak380mGazyli.Messages.Command;
import zak380mGazyli.Messages.Message;

public class Listener extends Thread {
    private ServerSocket serverSocket;
    private Server server;
    private Gson gson;

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
            ObjectOutputStream out = new ObjectOutputStream(playerSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(playerSocket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean setUpGamemode(String gamemodeName, int playerCount, int botCount) {
        System.out.println("Setting up gamemode: " + gamemodeName);
        GameBuilder gameBuilder = new GameBuilder(gamemodeName);
        GamemodeBuilder gamemodeBuilder = gameBuilder.getGamemodeBuilder();
        BoardBuilder boardBuilder = gameBuilder.getBoardBuilder();
        boardBuilder.buildBoard(playerCount);
        if(boardBuilder.getBoard() == null) {
            System.out.println("Board setup failed.");
            return false;
        }
        this.board = boardBuilder.getBoard();
        gamemodeBuilder.buildGamemode(playerCount, board);
        if(gamemodeBuilder.getGamemode() == null) {
            System.out.println("Gamemode setup failed.");
            return false;
        }
        this.gamemode = gamemodeBuilder.getGamemode();
        System.out.println("Gamemode set up is done.");
        return true;
    }

    private void setUpGamemode() {
        try {
            out.writeObject(gson.toJson(new Message("Send setUpGameMode.")));
            out.flush();
            String jsonString = (String) in.readObject();
            Command command = gson.fromJson(jsonString, Command.class);
            System.out.println("Player " + playerNumber + " sent command: " + command.getName());

            if ("setUpGamemode".equals(command.getName()) && command.getArgs().length == 2) {
                if (!room.setUpGamemode(command.getTextArg() , command.getArgs()[0], command.getArgs()[1])) {
                    sendErrorMessage("Try again, invalid setup.");
                }
                gamemode = room.getGamemode();
            } else {
                sendErrorMessage("Try again, invalid setup.");
            }
        } catch (SocketException e) {
            isConnected = false;
        } catch (IOException | ClassNotFoundException e) {
            sendErrorMessage(e.getMessage());
        }
    }
}
