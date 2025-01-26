package zak380mGazyli.Bots;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.PlayersHandling.GameRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasicBot implements Bot {
    private Gamemode gamemode;
    private boolean isConnected;
    private int playerNumber;
    private Board board;
    private String color = null;
    private int pawnAmount;
    private int scaleFactor;
    private int[][] oppositeStartingArea;
    private int[] oppositeCorner;
    private final Random rand = new Random();
    private Boolean goodMovesAviableFlag = false;

    public void setGame(GameRoom gameroom, Gamemode gamemode, Board board, int playerNumber) {
        this.gamemode = gamemode;
        this.playerNumber = playerNumber;
        this.isConnected = true;
        this.board = board;
        this.color = gamemode.getPlayerColor(playerNumber);
        this.pawnAmount = board.getStartArea(playerNumber).length;
        this.scaleFactor = calculateScaleFactor();
        this.oppositeStartingArea = findOppositeStartingArea();
        this.oppositeCorner = findOppositeCorner();
    }

    private int calculateScaleFactor() {
        double rows = board.getBoard().length;
        double columns = board.getBoard()[0].length;
        double ratio = rows / columns;
        return (int) Math.ceil(ratio*ratio);
    }

    private int[][] findOppositeStartingArea() {
        int startingAreasAmount = board.getNeighbours().length; // could be improved by getting the actual amount of starting areas
        int oppositeStartingAreaNumber = (playerNumber + startingAreasAmount / 2) % startingAreasAmount;
        return board.getStartArea(oppositeStartingAreaNumber);
    }

    private int[] findOppositeCorner() {
        int[] boardCenter = {board.getBoard().length / 2, board.getBoard()[0].length / 2};

        int[] oppositeCorner = boardCenter.clone();

        for (int[] cell : oppositeStartingArea) {
            int deltaX = Math.abs(cell[0] - boardCenter[0]);
            int deltaY = Math.abs(cell[1] - boardCenter[1]);

            if (deltaX * deltaX + deltaY * deltaY > oppositeCorner[0] * oppositeCorner[0] + oppositeCorner[1] * oppositeCorner[1]) {
                oppositeCorner[0] = cell[0];
                oppositeCorner[1] = cell[1];
            }
        }

        return oppositeCorner;
    }

    public void run() {
        while (isConnected) {
            if (gamemode.getTurn() == playerNumber) {
                int[] move = getBestMove();
                if (move != null && goodMovesAviableFlag) {
                    gamemode.processMove(move[0], move[1], move[2], move[3], board);
                }
            }
            if (gamemode.playerPlace().get(playerNumber) != 0) {
                isConnected = false;
            }
        }
    }

    private int[][] getPawnCoordinates() {
        if (color == null) {
            return null;
        }

        int[][] pawnsCoordinates = new int[pawnAmount][2];

        int currentPawn = 0;

        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                if (board.getBoard()[i][j].getColor().equals(color)) {
                    pawnsCoordinates[currentPawn][0] = i;
                    pawnsCoordinates[currentPawn][1] = j;
                    currentPawn++;
                }
            }
        }

        return pawnsCoordinates;
    }

    private int evaluateMove(int[] pawnCoordinates, int[] targetCoordinates) {
        int deltaX = Math.abs(pawnCoordinates[0] - targetCoordinates[0]);
        int deltaY = Math.abs(pawnCoordinates[1] - targetCoordinates[1]);

        int distanceMoved = scaleFactor * deltaX * deltaX + deltaY * deltaY;

        int startDeltaXToOppositeCorner = Math.abs(pawnCoordinates[0] - oppositeCorner[0]);
        int startDeltaYToOppositeCorner = Math.abs(pawnCoordinates[1] - oppositeCorner[1]);

        int startDistanceToOppositeCorner = scaleFactor * startDeltaXToOppositeCorner * startDeltaXToOppositeCorner + startDeltaYToOppositeCorner * startDeltaYToOppositeCorner;

        int endDeltaXToOppositeCorner = Math.abs(targetCoordinates[0] - oppositeCorner[0]);
        int endDeltaYToOppositeCorner = Math.abs(targetCoordinates[1] - oppositeCorner[1]);

        int endDistanceToOppositeCorner = scaleFactor * endDeltaXToOppositeCorner * endDeltaXToOppositeCorner + endDeltaYToOppositeCorner * endDeltaYToOppositeCorner;

        int distanceMovedTowardsOppositeCorner = startDistanceToOppositeCorner - endDistanceToOppositeCorner;

        if (distanceMovedTowardsOppositeCorner > 0) {
            goodMovesAviableFlag = true;
//        } else {
//            return 0;
        }

        return distanceMoved + distanceMovedTowardsOppositeCorner;
    }

    private int[] getBestMove() {
        int[][] pawnsCoordinates = getPawnCoordinates();
        if (pawnsCoordinates == null) {
            return null;
        }

        int bestMoveScore = 0;
        List<int[]> bestMoves = new ArrayList<>();

        for (int pawn = 0; pawn < pawnAmount; pawn++) {
            int[] pawnCoordinates = pawnsCoordinates[pawn];
            for (int i = 0; i < board.getBoard().length; i++) {
                for (int j = 0; j < board.getBoard()[0].length; j++) {
                    int[] targetCoordinates = {i, j};
                    if (gamemode.validateMove(pawnCoordinates[0], pawnCoordinates[1], targetCoordinates[0], targetCoordinates[1], board)) {
                        int moveScore = evaluateMove(pawnCoordinates, targetCoordinates);
                        if (moveScore > bestMoveScore) {
                            bestMoveScore = moveScore;
                            bestMoves.clear();
                            bestMoves.add(new int[]{pawnCoordinates[0], pawnCoordinates[1], targetCoordinates[0], targetCoordinates[1]});
                        } else if (moveScore == bestMoveScore) {
                            bestMoves.add(new int[]{pawnCoordinates[0], pawnCoordinates[1], targetCoordinates[0], targetCoordinates[1]});
                        }
                    }
                }
            }
        }

        return bestMoves.get(rand.nextInt(bestMoves.size()));
    }
}