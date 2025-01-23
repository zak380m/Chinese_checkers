package zak380mGazyli.Bots;

import zak380mGazyli.Gamemodes.Gamemode;

/**
 * The Bot interface represents a bot in the game that can be run as a separate thread.
 */
public interface Bot extends Runnable {
    /**
     * The setGamemode method sets the gamemode of the bot.
     */
    public void setGame(Gamemode gamemode, int playerID);
}
