package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Misc.Color;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SuperGamemode implements Gamemode {
    private int numberOfPlayers;
    private int currentPlayerTurn;
    private boolean pass;
    private Map<Integer, String> playerColors;
    private Map<Integer, Integer> playerPlace;
    private Map<Integer, Integer> playerAim;
    private int[] finishedPlayersRank;
    private int rankCounter;
    private int turnCount;

    public SuperGamemode() {
        this.numberOfPlayers = 0;
        this.currentPlayerTurn = 0;
        this.pass = false;
        this.playerColors = new HashMap<>();
        this.playerPlace = new HashMap<>();
        this.playerAim = new HashMap<>();
        this.finishedPlayersRank = new int[6];
        this.rankCounter = 1;
        this.turnCount = 0;
    }

    @Override
    public boolean setNumberOfPlayers(int numberOfPlayers, Board board) {
        if (numberOfPlayers != 2 && numberOfPlayers != 3 && numberOfPlayers != 4 && numberOfPlayers != 6) {
            return false;
        }

        this.numberOfPlayers = numberOfPlayers;
        initializePlayerColorsAndAims(board);
        return true;
    }

    private void initializePlayerColorsAndAims(Board board) {
        int j = 1;
        for (int i = 0; i < numberOfPlayers; i++) {
            while(board.getStartArea(j)[0].getColor() == Color.WHITE) j++;
            playerColors.put(i, board.getStartArea(j)[0].getColor());
            playerPlace.put(i, 0);
            playerAim.put(i, ((j - 1) + 3) % 6 + 1);
            j++;
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

        if (startX == endX && startY == endY) {
            return false;
        }

        if (isNeighbor(startX, startY, endX, endY)) {
            return true;
        }

        return catapultJumpCheck(startX, startY, endX, endY, board);
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

    private boolean catapultJumpCheck(int startX, int startY, int endX, int endY, Board board) {
        int[][] directions = {
            {0, 2}, {0, -2}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}
        };

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[board.getBoard().length][board.getBoard()[0].length];  
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0];
            int y = pos[1];

            for (int[] dir : directions) {
                int step = 1;
                while (true) {
                    int midX = x + dir[0] * step;
                    int midY = y + dir[1] * step;
                    int newX = x + dir[0] * (step + 1);
                    int newY = y + dir[1] * (step + 1);

                    if (newX < 0 || newX >= board.getBoard().length || newY < 0 || newY >= board.getBoard()[0].length) {
                        break;  
                    }

                    if (!board.getBoard()[midX][midY].getSymbol().equals("O")) {
                        break;  
                    }

                    if (board.getBoard()[newX][newY].getSymbol().equals(".")) {
                        if (newX == endX && newY == endY) {
                            return true;  
                        }
                        if (!visited[newX][newY]) {
                            queue.add(new int[]{newX, newY});
                            visited[newX][newY] = true;
                        }
                    }

                    step++;
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
        for(int i = 0; i < board.getStartArea(playerAim.get(player)).length; i++) {
            if (!board.getStartArea(playerAim.get(player))[i].getColor().equals(playerColors.get(player))) {
                return false;
            }
        }

        return true;
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
    public Map<Integer, Integer> playerPlace() {
        return playerPlace;
    }

    @Override
    public int getTurn() {
        return currentPlayerTurn;
    }

    private void nextTurn() {
        boolean stillPlaying = false;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (playerPlace.get(i) == 0) {
                stillPlaying = true;
                break;
            }
        }
        if(!stillPlaying) {
            currentPlayerTurn = -1;
            return;
        }
        do {
            currentPlayerTurn = (currentPlayerTurn + 1) % numberOfPlayers;
        } while (playerPlace.get(currentPlayerTurn) != 0);
        turnCount++;
    }
}
