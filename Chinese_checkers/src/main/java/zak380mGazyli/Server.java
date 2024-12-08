package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import zak380mGazyli.GameModes.*;
import zak380mGazyli.Boards.*;

public class Server {
    private List<ClientHandler> clients = new ArrayList<>();
    private GameMode gameMode = new DummyGameMode();  
    private Board board = new ClassicBoard();

    public static void main(String[] args) throws IOException {
        new Server().startServer();
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);
        board.initializeBoard();

        System.out.println("Server started on port 5555.");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected.");
            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }
    }

    public synchronized void processMove(int[] startPos, int[] endPos) {
        if (gameMode.validateMove(startPos, endPos, board)) {
            gameMode.processMove(startPos, endPos, board);
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
                int[] startPos = (int[]) in.readObject();
                int[] endPos = (int[]) in.readObject();
                server.processMove(startPos, endPos);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendBoard(String[][] board) {
        try {
            out.writeObject(board);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

