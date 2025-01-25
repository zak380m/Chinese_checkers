package zak380mGazyli.Database.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    public Game() {
    }

    public Game(String gamemode, String board, int playerNumber) {
        this.gamemode = gamemode;
        this.board = board;
        this.playerNumber = playerNumber;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public String getGamemode() {
        return gamemode;
    }

    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}