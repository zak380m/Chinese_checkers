package zak380mGazyli.Bots;

/**
 * The Bot interface represents a bot in the game that can be run as a separate thread.
 */
public interface Bot extends Runnable {
    /**
     * The run method is the entry point for the bot's execution when it is run as a thread.
     */
    public void run();
}
