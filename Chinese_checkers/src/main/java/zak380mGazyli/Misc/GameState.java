package zak380mGazyli.Misc;

import java.util.Map;

public class GameState {
    private int whoMoved;
    private Cell[][] boardState;
    private int playerNumber;
    private String playerColor;
    private int turnCount;
    private Map<Integer, Integer> playerPlace;

    private boolean isPass;

    public GameState(Cell[][] boardState, int playerNumber, int whoMoved, Map<Integer, Integer> playerPlace, boolean isPass, String playerColor, int turnCount) {
        this.boardState = boardState;
        this.playerNumber = playerNumber;
        this.whoMoved = whoMoved;
        this.playerPlace = playerPlace;
        this.isPass = isPass;
        this.playerColor = playerColor;
        this.turnCount = turnCount;
    }

    public int getWhoMoved() {
        return whoMoved;
    }

    public Cell[][] getBoardState() {
        return boardState;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Map<Integer, Integer> getPlayerPlace() {
        return playerPlace;
    }   

    public boolean getIsPass() {
        return isPass;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public int getTurnCount() {
        return turnCount;
    }
}
