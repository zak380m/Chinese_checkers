package zak380mGazyli;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import zak380mGazyli.Cells.Cell;
import zak380mGazyli.Displays.*;

public class Client {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Display display = new CLIDisplay();  

    public static void main(String[] args) throws IOException {
        new Client().startClient();
    }

    public void startClient() throws IOException {
        socket = new Socket("localhost", 5555);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Connected to server.");

        new Thread(() -> {
            try {
                while (true) {
                    Cell[][] board = (Cell[][]) in.readObject();
                    display.displayBoard(board);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();

        sendMoves();
    }

    private void sendMoves() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your move (startX startY endX endY): ");
            int startX = scanner.nextInt();
            int startY = scanner.nextInt();
            int endX = scanner.nextInt();
            int endY = scanner.nextInt();

            try {
                out.writeInt(startX);
                out.writeInt(startY);
                out.writeInt(endX);
                out.writeInt(endY);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

