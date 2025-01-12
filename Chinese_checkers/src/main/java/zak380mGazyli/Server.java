package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.PlayersHandling.GameRoom;
import zak380mGazyli.PlayersHandling.Listener;

public class Server {
    private List<GameRoom> gameRooms = new ArrayList<>();
    private int roomCounter = 0;

    public static void main(String[] args) throws IOException {
        new Server().startServer();
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);
        System.out.println("Server started on port 5555.");

        new Listener(serverSocket, this).start();
    }

    public synchronized GameRoom createGameRoom(Gamemode gamemode, Board board, String password, int numberOfPlayers, int numberOfBots) {
        GameRoom newRoom = new GameRoom(gamemode, board, password, numberOfPlayers, numberOfBots, roomCounter++);
        gameRooms.add(newRoom);
        return newRoom;
    }

    public synchronized GameRoom joinGameRoom(String gameName, String password) {
        for (GameRoom room : gameRooms) {
            if (room.getGamemodeName().equals(gameName) && room.getPassword().equals(password) && room.areThereMissingPlayers()) {
                return room;
            }
        }
        return null;  
    }
}

