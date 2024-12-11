package zak380mGazyli;

import zak380mGazyli.Cells.Cell;

public class GameState {
    private Cell[][] boardState;
    private int turnsBeforePlayer;

    public GameState(Cell[][] boardState, int turnsBeforePlayer) {
        this.boardState = boardState;
        this.turnsBeforePlayer = turnsBeforePlayer;
    }

    public Cell[][] getBoardState() {
        return boardState;
    }

    public int getTurnsBeforePlayer() {
        return turnsBeforePlayer;
    }
}
