package zak380mGazyli.Misc;

import java.util.Map;

public class GameState {
    private int whoMoved;
    private Cell[][] boardState;
    private int turnsBeforePlayer;
    private Map<Integer, Integer> playerPlace;
    private boolean isPass;

    public GameState(Cell[][] boardState, int turnsBeforePlayer, int whoMoved, Map<Integer, Integer> playerPlace, boolean isPass) {
        this.boardState = boardState;
        this.turnsBeforePlayer = turnsBeforePlayer;
        this.whoMoved = whoMoved;
        this.playerPlace = playerPlace;
        this.isPass = isPass;
    }

    public int getWhoMoved() {
        return whoMoved;
    }

    public Cell[][] getBoardState() {
        return boardState;
    }

    public int getTurnsBeforePlayer() {
        return turnsBeforePlayer;
    }

    public Map<Integer, Integer> getPlayerPlace() {
        return playerPlace;
    }   

    public boolean getIsPass() {
        return isPass;
    }
}
