package zak380mGazyli.Builders.GamemodeBuilders;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.BasicGamemode;
import zak380mGazyli.Gamemodes.Gamemode;

public class BasicGamemodeBuilder implements GamemodeBuilder {
    private Gamemode gamemode;

    @Override
    public void buildGamemode(int numberOfPlayers, Board board) {
        this.gamemode = new BasicGamemode();
        this.gamemode.setNumberOfPlayers(numberOfPlayers, board);
    }

    @Override
    public Gamemode getGamemode() {
        return this.gamemode;
    }
}
