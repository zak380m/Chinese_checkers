package zak380mGazyli.Database.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Represents a game entity in the database.
 */
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String gamemode;

    @Column(nullable = false)
    private String board;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "player_number", nullable = false)
    private int playerNumber = 2;

    // Bidirectional mapping with moves
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Move> moves;

    /**
     * Default constructor for the Game class.
     */
    public Game() {
    }

    /**
     * Constructs a Game with the specified parameters.
     *
     * @param gamemode the game mode
     * @param board the board
     * @param playerNumber the number of players
     */
    public Game(String gamemode, String board, int playerNumber) {
        this.gamemode = gamemode;
        this.board = board;
        this.playerNumber = playerNumber;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Gets the ID of the game.
     *
     * @return the game ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the game mode.
     *
     * @return the game mode
     */
    public String getGamemode() {
        return gamemode;
    }

    /**
     * Sets the game mode.
     *
     * @param gamemode the game mode
     */
    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }

    /**
     * Gets the board.
     *
     * @return the board
     */
    public String getBoard() {
        return board;
    }

    /**
     * Sets the board.
     *
     * @param board the board
     */
    public void setBoard(String board) {
        this.board = board;
    }

    /**
     * Gets the creation timestamp of the game.
     *
     * @return the creation timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the number of players.
     *
     * @return the number of players
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Sets the number of players.
     *
     * @param playerNumber the number of players
     */
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
