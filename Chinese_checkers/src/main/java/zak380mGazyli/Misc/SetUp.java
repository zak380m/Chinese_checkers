package zak380mGazyli.Misc;

import java.util.List;

public class SetUp {
    private boolean create;
    private String gamemode;
    private String board;
    private int playerCount;
    private String password;
    private int botCount;
    private List<String> gamemodes;
    private List<String> boards;

    public SetUp(List<String> gamemodes, List<String> boards) {
        this.create = false;
        this.gamemode = null;
        this.board = null;
        this.playerCount = 0;
        this.password = null;
        this.botCount = 0;
        this.gamemodes = gamemodes;
        this.boards = boards;
    }

    public SetUp() {
        this.create = false;
        this.gamemode = null;
        this.board = null;
        this.playerCount = 0;
        this.password = null;
        this.botCount = 0;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBotCount(int botCount) {
        this.botCount = botCount;
    }

    public boolean isCreate() {
        return create;
    }

    public String getGamemode() {
        return gamemode;
    }

    public String getBoard() {
        return board;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public String getPassword() {
        return password;
    }

    public int getBotCount() {
        return botCount;
    }

    public List<String> getGamemodes() {
        return gamemodes;
    }

    public List<String> getBoards() {
        return boards;
    }
}
