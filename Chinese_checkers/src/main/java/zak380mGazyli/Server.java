package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.PlayersHandling.*;;

public class Server {
    private Map<Integer, GameRoom> gameRooms = new HashMap<>();
    private int roomCounter = 0;

    public static void main(String[] args) throws IOException {
        new Server().startServer();
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);
        System.out.println("Server started on port 5555.");
        while (true) {
            try {
                Socket playerSocket = serverSocket.accept();
                System.out.println("New player connected.");
                PlayerHandler playerHandler = new PlayerHandler(playerSocket, this);
                new Thread(playerHandler).start();
            } catch (IOException e) {
                System.out.println("Failed to accept player connection");
            }
        }
    }

    public synchronized GameRoom createGameRoom(Gamemode gamemode, Board board, String password, int numberOfPlayers, int numberOfBots) {
        GameRoom newRoom = new GameRoom(gamemode, board, password, numberOfPlayers, numberOfBots, roomCounter++, this);
        for(int i = 1; i <= roomCounter; i++) {
            if(gameRooms.get(i) == null) {
                gameRooms.put(i, newRoom);
                return newRoom;
            }
        }
        gameRooms.put(roomCounter, newRoom);
        return newRoom;
    }

    public synchronized GameRoom joinGameRoom(String gameName, String password) {
        for (int i = 1; i <= roomCounter; i++) {
            if (gameRooms.get(i).getGamemodeName().equals(gameName) && gameRooms.get(i).getPassword().equals(password) && gameRooms.get(i).areThereMissingPlayers()) {
                return gameRooms.get(i);
            }
        }
        return null;  
    }

    public synchronized void deleteGameRoom(GameRoom room) {
        for (int i = 1; i <= roomCounter; i++) {
            if (gameRooms.get(i) == room) {
                gameRooms.put(i, null);
                if(i == roomCounter ) {
                    roomCounter--;
                }
                break;
            }
        }
    }
}