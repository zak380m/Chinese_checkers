package zak380mGazyli;

import zak380mGazyli.Misc.Cell;

public class GameState {
    private int whoMoved;
    private Cell[][] boardState;
    private int turnsBeforePlayer;

    public GameState(Cell[][] boardState, int turnsBeforePlayer, int whoMoved) {
        this.boardState = boardState;
        this.turnsBeforePlayer = turnsBeforePlayer;
        this.whoMoved = whoMoved;
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
}
