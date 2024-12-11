package zak380mGazyli.Builders.GamemodeBuilders;

import zak380mGazyli.Gamemodes.Gamemode;

public interface GamemodeBuilder {
    void buildGamemode(int numberOfPlayers);
    Gamemode getGamemode();
}
