package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.*;

public class DummyGamemode implements Gamemode {

    private int turn = 0;
    private int numberOfPlayers = 1;

    @Override
    public boolean setNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers < 2 || numberOfPlayers > 6 || numberOfPlayers == 5) {
            return false;
        }
        this.numberOfPlayers = numberOfPlayers;
        return true;
    }

    @Override
    public boolean validateMove(int startX, int startY, int endX, int endY, Board board) {
        return true;
    }

    @Override
    public void processMove(int startX, int startY, int endX, int endY, Board board) {
        board.updateBoard(startX, startY, endX, endY);
        turn++;
    }

    @Override
    public int getTurn() {
        return turn%numberOfPlayers;
    }
}
