package zak380mGazyli.Displays;

/**
 * The Display interface provides methods to display the game interface,
 * retrieve commands, and quit the display.
 */
public interface Display {
    /**
     * Displays the game interface based on the provided board state.
     *
     * @param board The board state to be displayed.
     */
    public void displayInterface(String board);

    /**
     * Retrieves the current commands as a JSON string.
     *
     * @return The current commands as a JSON string.
     */
    public String getCommands();

    /**
     * Quits the display.
     */
    public void quit();
}