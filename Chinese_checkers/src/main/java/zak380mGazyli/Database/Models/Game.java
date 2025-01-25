package zak380mGazyli.Database.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String gamemode;

    @Column(nullable = false, length = 100)
    private String board;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Move> moves;

    // Constructors, Getters, Setters, etc.

    public Game() {}

    public Game(String gamemode, String board) {
        this.gamemode = gamemode;
        this.board = board;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Move> getMoves() {
        return moves;
    }

    public void setMoves(Set<Move> moves) {
        this.moves = moves;
    }
}
