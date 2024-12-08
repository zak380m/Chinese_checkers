package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import zak380mGazyli.Boards.*;
import zak380mGazyli.Builders.GamemodeBuilders.*;
import zak380mGazyli.Cells.Cell;
import zak380mGazyli.Builders.BoardBuilders.*;
import zak380mGazyli.Gamemodes.*;

public class Server {
    private GamemodeBuilder gamemodeBuilder = new DummyGamemodeBuilder();
    private BoardBuilder boardBuilder = new ClassicBoardBuilder();
    private List<ClientHandler> clients = new ArrayList<>();
    private Gamemode gamemode;  
    private Board board;

    public static void main(String[] args) throws IOException {
        new Server().startServer();
    }

    public void startServer() throws IOException {
        gamemodeBuilder.buildGamemode();
        gamemode = gamemodeBuilder.getGamemode();
        boardBuilder.buildBoard(2);
        board = boardBuilder.getBoard();
        ServerSocket serverSocket = new ServerSocket(5555);

        System.out.println("Server started on port 5555.");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected.");
            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }
    }

    public synchronized void processMove(int startX, int startY, int endX, int endY) {
        if (gamemode.validateMove(startX, startY, endX, endY, board)) {
            gamemode.processMove(startX, startY, endX, endY, board);
            broadcastBoard();
        }
    }

    public synchronized void broadcastBoard() {
        for (ClientHandler client : clients) {
            client.sendBoard(board.getBoard());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                int startX = (int) in.readInt();
                int startY = (int) in.readInt();
                int endX = (int) in.readInt();
                int endY = (int) in.readInt();
                server.processMove(startX, startY, endX, endY);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBoard(Cell[][] board) {
        try {
            out.writeObject(board);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

