package zak380mGazyli;

import zak380mGazyli.Boards.ClassicBoard;
import zak380mGazyli.Cells.ClassicBoardCell;

public class GameState {
    private ClassicBoardCell[][] boardState;
    private int turnsBeforePlayer;

    public GameState(ClassicBoardCell[][] boardState, int turnsBeforePlayer) {
        this.boardState = boardState;
        this.turnsBeforePlayer = turnsBeforePlayer;
    }

    public ClassicBoardCell[][] getBoardState() {
        return boardState;
    }

    public int getTurnsBeforePlayer() {
        return turnsBeforePlayer;
    }
}
