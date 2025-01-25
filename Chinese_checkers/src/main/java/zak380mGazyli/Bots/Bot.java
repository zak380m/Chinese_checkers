package zak380mGazyli.Bots;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.PlayersHandling.GameRoom;

/**
 * The Bot interface represents a bot in the game that can be run as a separate thread.
 */
public interface Bot extends Runnable {
    /**
     * The setGamemode method sets the gamemode of the bot.
     */
    void setGame(GameRoom gameroom, Gamemode gamemode, Board board, int playerNumber);

}
