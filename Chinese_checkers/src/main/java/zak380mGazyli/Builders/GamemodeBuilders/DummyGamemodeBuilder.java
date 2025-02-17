package zak380mGazyli.Builders.GamemodeBuilders;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.DummyGamemode;
import zak380mGazyli.Gamemodes.Gamemode;

public class DummyGamemodeBuilder implements GamemodeBuilder {
    private Gamemode gamemode;

    @Override
    public void buildGamemode(int numberOfPlayers, Board board) {
        this.gamemode = new DummyGamemode();
        this.gamemode.setNumberOfPlayers(numberOfPlayers, board);
    }

    @Override
    public Gamemode getGamemode() {
        return this.gamemode;
    }
}
