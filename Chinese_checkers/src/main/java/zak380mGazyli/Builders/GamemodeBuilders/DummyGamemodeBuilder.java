package zak380mGazyli.Builders.GamemodeBuilders;

import zak380mGazyli.Gamemodes.DummyGamemode;
import zak380mGazyli.Gamemodes.Gamemode;

public class DummyGamemodeBuilder implements GamemodeBuilder {
    private Gamemode gamemode;

    @Override
    public void buildGamemode() {
        this.gamemode = new DummyGamemode();
    }

    @Override
    public Gamemode getGamemode() {
        return this.gamemode;
    }
}
