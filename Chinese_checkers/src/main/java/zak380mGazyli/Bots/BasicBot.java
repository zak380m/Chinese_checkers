package zak380mGazyli.Bots;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.PlayersHandling.GameRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BasicBot implements Bot {
    private GameRoom gameroom;
    private Gamemode gamemode;
    private boolean isConnected;
    private int playerNumber;
    private Board board;
    private String color = null;
    private int pawnAmount;
    private int scaleFactor;
    private int startingAreasAmount;
    private int[] boardCenter;
    private int[][] oppositeStartingArea;
    private int[] targetCell;
    private int[] OriginalTargetCell;
    private final Random rand = new Random();
    private final List<int[]> unmovablePawns = new ArrayList<>();

    public void setGame(GameRoom gameroom, Gamemode gamemode, Board board, int playerNumber) {
        this.gameroom = gameroom;
        this.gamemode = gamemode;
        this.playerNumber = playerNumber;
        this.isConnected = true;
        this.board = board;
        this.color = gamemode.getPlayerColor(playerNumber);
        this.pawnAmount = board.getStartArea(playerNumber).length;
        this.scaleFactor = calculateScaleFactor();
        this.startingAreasAmount = board.getNeighbours().length;
        this.boardCenter = new int[]{board.getBoard().length / 2, board.getBoard()[0].length / 2};
        this.oppositeStartingArea = findOppositeStartingArea();
        this.OriginalTargetCell = findTargetCell();
    }

    private int calculateScaleFactor() {
        double rows = board.getBoard().length;
        double columns = board.getBoard()[0].length;
        double ratio = columns / rows;
        return (int) Math.ceil(ratio*ratio);
    }

    private int getStartingAreaNumber() {
        for (int i = 1; i <= startingAreasAmount; i++) {
            if (board.getBoard()[board.getStartArea(i)[0][0]][board.getStartArea(i)[0][1]].getColor().equals(color)) {
                System.out.println("Bot " + playerNumber + " is starting in area: " + i);
                return i;
            }
        }
        return -1;
    }

    private int[][] findOppositeStartingArea() {
        int playerStartingAreaNumber = getStartingAreaNumber();
        int oppositeStartingAreaNumber = ((playerStartingAreaNumber - 1)+ startingAreasAmount / 2) % startingAreasAmount + 1;
        System.out.println("Bot " + playerNumber + " is opposite to area: " + oppositeStartingAreaNumber);
        return board.getStartArea(oppositeStartingAreaNumber);
    }

    private int[] findTargetCell() {
        int[] targetCell = new int[2];

        int previousDistanceToCenter = 0;

        for (int[] cell : oppositeStartingArea) {
            if (!board.getBoard()[cell[0]][cell[1]].getColor().equals(color)) {
                int deltaX = Math.abs(cell[0] - boardCenter[0]);
                int deltaY = Math.abs(cell[1] - boardCenter[1]);

                int distanceToCenter = scaleFactor * deltaX * deltaX + deltaY * deltaY;

                if (distanceToCenter > previousDistanceToCenter) {
                    previousDistanceToCenter = distanceToCenter;
                    targetCell[0] = cell[0];
                    targetCell[1] = cell[1];
                }
            }
        }

        return targetCell;
    }

    public void run() {
        while (isConnected) {
            if (gamemode.getTurn() == playerNumber) {
                int[] move = getBestMove();
                if (move != null && gamemode.playerPlace().get(playerNumber) == 0) {
                    System.out.println("Bot " + playerNumber + " is making move: " + Arrays.toString(move));
                    gameroom.processMove(move[0], move[1], move[2], move[3]);
                } else {
                    System.out.println("Bot " + playerNumber + " is passing");
                    gameroom.processPass();
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


        int startDeltaXToTargetCell = Math.abs(pawnCoordinates[0] - targetCell[0]);
        int startDeltaYToTargetCell = Math.abs(pawnCoordinates[1] - targetCell[1]);

        int startDistanceToTargetCell = scaleFactor * startDeltaXToTargetCell * startDeltaXToTargetCell + startDeltaYToTargetCell * startDeltaYToTargetCell;

        int endDeltaXToTargetCell = Math.abs(targetCoordinates[0] - targetCell[0]);
        int endDeltaYToTargetCell = Math.abs(targetCoordinates[1] - targetCell[1]);

        int endDistanceToTargetCell = scaleFactor * endDeltaXToTargetCell * endDeltaXToTargetCell + endDeltaYToTargetCell * endDeltaYToTargetCell;

        int distanceMovedTowardsTargetCell = startDistanceToTargetCell - endDistanceToTargetCell;


        int startDeltaXToOriginalTargetCell = Math.abs(pawnCoordinates[0] - OriginalTargetCell[0]);
        int startDeltaYToOriginalTargetCell = Math.abs(pawnCoordinates[1] - OriginalTargetCell[1]);

        int startDistanceToOriginalTargetCell = scaleFactor * startDeltaXToOriginalTargetCell * startDeltaXToOriginalTargetCell + startDeltaYToOriginalTargetCell * startDeltaYToOriginalTargetCell;

        int endDeltaXToOriginalTargetCell = Math.abs(targetCoordinates[0] - OriginalTargetCell[0]);
        int endDeltaYToOriginalTargetCell = Math.abs(targetCoordinates[1] - OriginalTargetCell[1]);

        int endDistanceToOriginalTargetCell = scaleFactor * endDeltaXToOriginalTargetCell * endDeltaXToOriginalTargetCell + endDeltaYToOriginalTargetCell * endDeltaYToOriginalTargetCell;

        int distanceMovedTowardsOriginalTargetCell = startDistanceToOriginalTargetCell - endDistanceToOriginalTargetCell;


        if ((distanceMovedTowardsTargetCell + distanceMovedTowardsOriginalTargetCell) / 2 <= 0) {
            return 0;
        }

        for (int[] oppositeCell : oppositeStartingArea) {
            if (pawnCoordinates[0] == oppositeCell[0] && pawnCoordinates[1] == oppositeCell[1]) {
                return (distanceMoved + (distanceMovedTowardsTargetCell + distanceMovedTowardsOriginalTargetCell) / 2) / 2;
            }
        }

        return distanceMoved + (distanceMovedTowardsTargetCell + distanceMovedTowardsOriginalTargetCell) / 2;
    }

    private int[] getBestMove() {
        targetCell = findTargetCell();

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

                    boolean isMovable = true;
                    for (int[] unmovablePawn : unmovablePawns) {
                        if (pawnCoordinates[0] == unmovablePawn[0] && pawnCoordinates[1] == unmovablePawn[1]) {
                            isMovable = false;
                            break;
                        }
                    }

                    if (gamemode.validateMove(pawnCoordinates[1], pawnCoordinates[0], targetCoordinates[1], targetCoordinates[0], board) && isMovable) {
                        int moveScore = evaluateMove(pawnCoordinates, targetCoordinates);
                        if (moveScore > bestMoveScore) {
                            bestMoveScore = moveScore;
                            bestMoves.clear();
                            bestMoves.add(new int[]{pawnCoordinates[1], pawnCoordinates[0], targetCoordinates[1], targetCoordinates[0]});
                        } else if (moveScore == bestMoveScore) {
                            bestMoves.add(new int[]{pawnCoordinates[1], pawnCoordinates[0], targetCoordinates[1], targetCoordinates[0]});
                        }
                    }
                }
            }
        }

        System.out.println("Bot " + playerNumber + " is considering moves: " + Arrays.deepToString(bestMoves.toArray()) + " with score: " + bestMoveScore);

        int[] bestMove = bestMoves.get(rand.nextInt(bestMoves.size()));

        if (bestMove[3] == targetCell[0] && bestMove[2] == targetCell[1]) {
            unmovablePawns.add(new int[]{bestMove[3], bestMove[2]});
        }

        return bestMove;
    }
}