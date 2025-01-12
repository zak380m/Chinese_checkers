package zak380mGazyli.PlayersHandling;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;

import zak380mGazyli.Server;
import zak380mGazyli.Boards.Board;
import zak380mGazyli.Builders.GameBuilder;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.Messages.ErrorMessage;
import zak380mGazyli.Misc.SetUp;

public class Listener extends Thread {
    private ServerSocket serverSocket;
    private Server server;
    private Gson gson;
    private GameBuilder gameBuilder;

    public Listener(ServerSocket serverSocket, Server server) {
        this.serverSocket = serverSocket;
        this.server = server;
        this.gameBuilder = new GameBuilder();
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
            boolean isSetUp = false;

            SetUp setUp = new SetUp(gameBuilder.getGameList(), gameBuilder.getBoardList());
            out.writeObject(gson.toJson(setUp));

            while(!isSetUp) {
                try {
                    String jsonString = (String) in.readObject();
                    SetUp info = gson.fromJson(jsonString, SetUp.class);
                    if (info.isCreate()) {
                        if(createGameRoom(info.getGamemode(), info.getPlayerCount(), info.getBotCount(), info.getPassword(), playerSocket)) {
                            isSetUp = true;
                        } else {
                            ErrorMessage errors = new ErrorMessage("Try again, invalid room data.");
                            out.writeObject(gson.toJson(errors));
                        }
                    } else {
                        GameRoom gameRoom = server.joinGameRoom(info.getGamemode(), info.getPassword());
                        if(gameRoom != null) {
                            isSetUp = gameRoom.addPlayer(playerSocket);
                        } else {
                            ErrorMessage errors = new ErrorMessage("Try again, invalid room data.");
                            out.writeObject(gson.toJson(errors));
                        }
                    }
                } catch (ClassNotFoundException e) {
                    ErrorMessage errors = new ErrorMessage("Try again, wrong setup.");
                    out.writeObject(gson.toJson(errors));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean createGameRoom(String gamemodeName, int playerCount, int botCount, String password, Socket playerSocket) {
        System.out.println("Setting up gamemode: " + gamemodeName);
        gameBuilder.setGameName(gamemodeName);
        GamemodeBuilder gamemodeBuilder = gameBuilder.getGamemodeBuilder();
        BoardBuilder boardBuilder = gameBuilder.getBoardBuilder();
        boardBuilder.buildBoard(playerCount);
        if(boardBuilder.getBoard() == null) {
            System.out.println("Board setup failed.");
            return false;
        }
        Board board = boardBuilder.getBoard();
        gamemodeBuilder.buildGamemode(playerCount, board);
        if(gamemodeBuilder.getGamemode() == null) {
            System.out.println("Gamemode setup failed.");
            return false;
        }
        Gamemode gamemode = gamemodeBuilder.getGamemode();
        System.out.println("Gamemode set up is done.");
        GameRoom gameRoom = server.createGameRoom(gamemode, board, password, playerCount, botCount);
        if(gameRoom != null) {
            System.out.println("Game room created.");
            gameRoom.addPlayer(playerSocket);
        } else {
            System.out.println("Game room creation failed.");
            return false;
        }
        return true;
    }
}
