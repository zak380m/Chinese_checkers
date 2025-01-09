package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Misc.Color;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BasicGamemode implements Gamemode {
    private int numberOfPlayers;
    private int currentPlayerTurn;
    private boolean pass;
    private Map<Integer, String> playerColors;
    private Map<Integer, Integer> playerPlace;
    private int[] finishedPlayersRank;
    private int rankCounter;
    private int turnCount;

    public BasicGamemode() {
        this.numberOfPlayers = 0;
        this.currentPlayerTurn = 0;
        this.pass = false;
        this.playerColors = new HashMap<>();
        this.playerPlace = new HashMap<>();
        this.finishedPlayersRank = new int[6];
        this.rankCounter = 1;
        this.turnCount = 0;
    }

    @Override
    public boolean setNumberOfPlayers(int numberOfPlayers) {
        if (numberOfPlayers != 2 && numberOfPlayers != 3 && numberOfPlayers != 4 && numberOfPlayers != 6) {
            return false; 
        }

        this.numberOfPlayers = numberOfPlayers;
        initializePlayerColors();
        return true;
    }

    private void initializePlayerColors() {
        String[] availableColors = {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.MAGENTA, Color.CYAN};

        for (int i = 0; i < numberOfPlayers; i++) {
            playerColors.put(i, availableColors[i]);
            playerPlace.put(i, 0);  
        }
    }

    @Override
    public boolean validateMove(int startX, int startY, int endX, int endY, Board board) {
        if (!board.getBoard()[startX][startY].getColor().equals(playerColors.get(currentPlayerTurn))) {
            return false;
        }

        if (!board.getBoard()[endX][endY].getSymbol().equals(".")) {
            return false;
        }

        if (isNeighbor(startX, startY, endX, endY)) {
            return true;
        }

        return bfsJumpCheck(startX, startY, endX, endY, board);
    }

    private boolean isNeighbor(int startX, int startY, int endX, int endY) {
        int[][] directions = {
                {0, 2}, {0, -2}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}
        };

        for (int[] dir : directions) {
            int newX = startX + dir[0];
            int newY = startY + dir[1];
            if (newX == endX && newY == endY) {
                return true;
            }
        }

        return false;
    }

    private boolean bfsJumpCheck(int startX, int startY, int endX, int endY, Board board) {
        int[][] directions = {
                {0, 2}, {0, -2}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}
        };

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[board.getRows()][board.getCols()];  
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0];
            int y = pos[1];

            for (int[] dir : directions) {
                int midX = x + dir[0] / 2;
                int midY = y + dir[1] / 2;
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX == endX && newY == endY && board.getBoard()[midX][midY] != null && !board.getBoard()[midX][midY].equals(".")) {
                    return true;  // Valid jump path
                }

                if (newX >= 0 && newX < board.getRows() && newY >= 0 && newY < board.getCols() && !visited[newX][newY] &&
                        board.getBoard()[midX][midY] != null && !board.getBoard()[midX][midY].equals(".") &&
                        board.getBoard()[midX][midY].equals(".")) {
                    queue.add(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }

        return false;
    }

    @Override
    public void processMove(int startX, int startY, int endX, int endY, Board board) {
        board.updateBoard(startX, startY, endX, endY);
        
        if (checkPlayerWon(board, currentPlayerTurn)) {
            updatePlayerRanking(currentPlayerTurn);
        }
        nextTurn();
    }

    private boolean checkPlayerWon(Board board, int player) {
        String playerColor = playerColors.get(player);

        return board.areAllPiecesInHome(playerColor);
    }

    private void updatePlayerRanking(int player) {
        if (playerPlace.get(player) == 0) {  
            playerPlace.put(player, rankCounter);
            finishedPlayersRank[rankCounter - 1] = player;
            rankCounter++;
        }
    }

    @Override
    public void processPass() {
        pass = true;
        nextTurn();
    }

    @Override
    public boolean isPass() {
        return pass;
    }

    @Override
    public int playerPlace(int playerNumber) {
        return playerPlace.get(playerNumber);
    }

    @Override
    public int getTurn() {
        return currentPlayerTurn;
    }

    private void nextTurn() {
        do {
            currentPlayerTurn = (currentPlayerTurn + 1) % numberOfPlayers;
        } while (playerPlace.get(currentPlayerTurn) != 0);
        turnCount++;
    }
}
