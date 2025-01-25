package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import zak380mGazyli.Boards.Board;
import zak380mGazyli.Database.Models.Game;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.PlayersHandling.*;

/**
 * The Server class represents a game server that manages game rooms and player connections.
 */
@SpringBootApplication
public class Server{
    private final Map<Integer, GameRoom> gameRooms = new HashMap<>();
    private int roomCounter = 0;

    @Autowired
    private Mediator mediator;

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @PostConstruct
    public void init() {
        try {
            startServer();
        } catch (IOException e) {
            System.out.println("Failed to start the server: " + e.getMessage());
        }
    }

    /**
     * Starts the server, listens for player connections, and handles them.
     *
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Creates a new game room with the specified parameters.
     *
     * @param gamemode The game mode for the new room.
     * @param board The board for the new room.
     * @param password The password for the new room.
     * @param numberOfPlayers The number of players in the new room.
     * @param numberOfBots The number of bots in the new room.
     * @return The created GameRoom instance.
     */
    public synchronized GameRoom createGameRoom(Gamemode gamemode, Board board, String password, int numberOfPlayers, int numberOfBots, Game game) {
        GameRoom newRoom = new GameRoom(gamemode, board, password, numberOfPlayers, numberOfBots, roomCounter++, this, mediator);
        if((newRoom != null) && (game == null) ) {
            game = saveGameToDatabase(gamemode, board, numberOfBots + numberOfPlayers);
        }
        newRoom.setGame(game);
        for(int i = 1; i <= roomCounter; i++) {
            if(gameRooms.get(i) == null) {
                gameRooms.put(i, newRoom);
                return newRoom;
            }
        }
        gameRooms.put(roomCounter, newRoom);
        return newRoom;
    }

    private Game saveGameToDatabase(Gamemode gamemode, Board board, int numberOfPlayers) {
        try {
            Game newGame = new Game(gamemode.getName(), board.getName(), numberOfPlayers);
            mediator.addGame(newGame);
            System.out.println("Game saved to the database with ID: " + newGame.getId());
            return newGame;
        } catch (Exception e) {
            System.out.println("Failed to save the game to the database: " + e.getMessage());
            return null;
        }
    }

    /**
     * Joins an existing game room with the specified game name and password.
     *
     * @param gameName The name of the game to join.
     * @param password The password for the game room.
     * @return The joined GameRoom instance, or null if no matching room is found.
     */
    public synchronized GameRoom joinGameRoom(String gameName, String password) {
        for (int i = 1; i <= roomCounter; i++) {
            if (gameRooms.get(i).getGamemodeName().equals(gameName) && gameRooms.get(i).getPassword().equals(password) && gameRooms.get(i).areThereMissingPlayers()) {
                return gameRooms.get(i);
            }
        }
        return null;
    }

    /**
     * Deletes the specified game room.
     *
     * @param room The GameRoom instance to delete.
     */
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

    public synchronized void deleteGameRoomByID(int gameID) {
        mediator.deleteGame(gameID);
    }
}
