package zak380mGazyli;

import zak380mGazyli.Misc.Cell;

public class GameState {
    private int whoMoved;
    private Cell[][] boardState;
    private int turnsBeforePlayer;
    private boolean isTerminal;
    private boolean isDraw;
    private boolean isPass;

    public GameState(Cell[][] boardState, int turnsBeforePlayer, int whoMoved, boolean isTerminal, boolean isDraw, boolean isPass) {
        this.boardState = boardState;
        this.turnsBeforePlayer = turnsBeforePlayer;
        this.whoMoved = whoMoved;
        this.isTerminal = isTerminal;
        this.isDraw = isDraw;
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

    public boolean getIsTerminal() {
        return isTerminal;
    }

    public boolean getIsDraw() {
        return isDraw;
    }

    public boolean getIsPass() {
        return isPass;
    }
}
