package zak380mGazyli.Bots;

import zak380mGazyli.Gamemodes.Gamemode;

/**
 * The DummyBot class implements the Bot interface and represents a dummy bot in the game.
 */
public class DummyBot implements Bot {
    Gamemode gamemode;
    boolean isConnected;
    int playerID;
    /**
     * The setGamemode method sets the gamemode of the bot.
     */
    public void setGame(Gamemode gamemode, int playerID) {
        this.gamemode = gamemode;
        this.playerID = playerID;
        this.isConnected = true;
    }

    /**
     * The run method runs the bot as a separate thread.
     */
    public void run() {
        while(isConnected) {
            if(gamemode.getTurn() == playerID) {
                gamemode.processPass();
            }
            if(gamemode.playerPlace().get(playerID) != 0) {
                isConnected = false;
            }
        }
    }
}
