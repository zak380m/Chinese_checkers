package zak380mGazyli.Misc;

import zak380mGazyli.Messages.Command;

import java.util.List;

/**
 * The SetUp class represents the setup configuration for a game.
 */
public class SetUp extends Command {
    private boolean load;
    private boolean create;
    private int gameId;
    private String gamemode;
    private String board;
    private int playerCount;
    private String password;
    private int botCount;
    private List<String> gamemodes;
    private List<String> boards;

    /**
     * Constructs a SetUp instance with the specified game modes and boards.
     *
     * @param gamemodes The list of available game modes.
     * @param boards The list of available boards.
     */
    public SetUp(List<String> gamemodes, List<String> boards) {
        this.gameId = 0;
        this.load = false;
        this.create = false;
        this.gamemode = null;
        this.board = null;
        this.playerCount = 0;
        this.password = null;
        this.botCount = 0;
        this.gamemodes = gamemodes;
        this.boards = boards;
    }

    /**
     * Constructs a default SetUp instance.
     */
    public SetUp() {
        this.gameId = 0;
        this.load = false;
        this.create = false;
        this.gamemode = null;
        this.board = null;
        this.playerCount = 0;
        this.password = null;
        this.botCount = 0;
    }

    /**
     * Sets whether to create a new game.
     *
     * @param create true to create a new game, false otherwise.
     */
    public void setCreate(boolean create) {
        this.create = create;
    }

    /**
     * Sets whether to load an existing game.
     *
     * @param load true to load an existing game, false otherwise.
     */
    public void setLoad(boolean load) {
        this.load = load;
    }

    /**
     * Sets the game ID.
     *
     * @param gameId The ID of the game.
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    /**
     * Sets the game mode.
     *
     * @param gamemode The game mode to set.
     */
    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }

    /**
     * Sets the board.
     *
     * @param board The board to set.
     */
    public void setBoard(String board) {
        this.board = board;
    }

    /**
     * Sets the player count.
     *
     * @param playerCount The number of players.
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    /**
     * Sets the password.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the bot count.
     *
     * @param botCount The number of bots.
     */
    public void setBotCount(int botCount) {
        this.botCount = botCount;
    }

    /**
     * Checks if a new game is to be created.
     *
     * @return true if a new game is to be created, false otherwise.
     */
    public boolean isCreate() {
        return create;
    }

    /**
     * Checks if an existing game is to be loaded.
     *
     * @return true if an existing game is to be loaded, false otherwise.
     */
    public boolean isLoad() {
        return load;
    }

    /**
     * Gets the game ID.
     *
     * @return The ID of the game.
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Gets the game mode.
     *
     * @return The game mode.
     */
    public String getGamemode() {
        return gamemode;
    }

    /**
     * Gets the board.
     *
     * @return The board.
     */
    public String getBoard() {
        return board;
    }

    /**
     * Gets the player count.
     *
     * @return The number of players.
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Gets the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the bot count.
     *
     * @return The number of bots.
     */
    public int getBotCount() {
        return botCount;
    }

    /**
     * Gets the list of available game modes.
     *
     * @return The list of available game modes.
     */
    public List<String> getGamemodes() {
        return gamemodes;
    }

    /**
     * Gets the list of available boards.
     *
     * @return The list of available boards.
     */
    public List<String> getBoards() {
        return boards;
    }
}
