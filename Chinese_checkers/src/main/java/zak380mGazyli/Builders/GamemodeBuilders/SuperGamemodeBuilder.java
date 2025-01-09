package zak380mGazyli.Builders.GamemodeBuilders;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.Gamemodes.SuperGamemode;

public class SuperGamemodeBuilder implements GamemodeBuilder {
    private Gamemode gamemode;

    @Override
    public void buildGamemode(int numberOfPlayers, Board board) {
        this.gamemode = new SuperGamemode();
        this.gamemode.setNumberOfPlayers(numberOfPlayers, board);
    }

    @Override
    public Gamemode getGamemode() {
        return this.gamemode;
    }
}