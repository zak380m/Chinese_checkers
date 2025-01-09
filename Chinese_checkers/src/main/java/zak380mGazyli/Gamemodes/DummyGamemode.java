package zak380mGazyli.Gamemodes;

import zak380mGazyli.Boards.*;

public class DummyGamemode implements Gamemode {

    private int turn = 0;
    private int numberOfPlayers = 1;
    private boolean pass = false;

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
        pass = false;
    }

    @Override
    public void processPass() {
        turn++;
        pass = true;
    }

    @Override
    public int getTurn() {
        return turn%numberOfPlayers;
    }

    @Override
    public boolean isPass() {
        return pass;
    }

    @Override 
    public int playerPlace(int playerNumber) {
        return 0;
    }
}
