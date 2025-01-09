package zak380mGazyli;

import zak380mGazyli.Misc.Cell;

public class GameState {
    private int whoMoved;
    private Cell[][] boardState;
    private int turnsBeforePlayer;
    private int playerPlace;
    private boolean isPass;

    public GameState(Cell[][] boardState, int turnsBeforePlayer, int whoMoved, int playerPlace, boolean isPass) {
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

    public int getPlayerPlace() {
        return playerPlace;
    }   

    public boolean getIsPass() {
        return isPass;
    }
}
