package zak380mGazyli.Builders.GamemodeBuilders;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.Gamemode;

public interface GamemodeBuilder {
    void buildGamemode(int numberOfPlayers, Board board);
    Gamemode getGamemode();
}
