package zak380mGazyli.Database.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "moves")
@IdClass(MoveId.class)
public class Move {

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;  // This is mapped to the game_id

    @Id
    @Column(name = "turn_number", nullable = false)
    private int turnNumber;

    @Column(name = "start_x")
    private int startX;

    @Column(name = "start_y")
    private int startY;

    @Column(name = "end_x")
    private int endX;

    @Column(name = "end_y")
    private int endY;

    @Column(name = "is_pass", nullable = false)
    private boolean isPass = false;

    @Column(name = "move_timestamp", nullable = false)
    private Timestamp moveTimestamp;

    public Move() {}

    public Move(Game game, int turnNumber, int startX, int startY, int endX, int endY, boolean isPass) {
        this.game = game;
        this.turnNumber = turnNumber;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.isPass = isPass;
        this.moveTimestamp = new Timestamp(System.currentTimeMillis());
    }

    // Getters and setters
    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    public int getTurnNumber() { return turnNumber; }
    public void setTurnNumber(int turnNumber) { this.turnNumber = turnNumber; }

    public int getStartX() { return startX; }
    public void setStartX(int startX) { this.startX = startX; }

    public int getStartY() { return startY; }
    public void setStartY(int startY) { this.startY = startY; }

    public int getEndX() { return endX; }
    public void setEndX(int endX) { this.endX = endX; }

    public int getEndY() { return endY; }
    public void setEndY(int endY) { this.endY = endY; }

    public boolean isPass() { return isPass; }
    public void setPass(boolean pass) { isPass = pass; }

    public Timestamp getMoveTimestamp() { return moveTimestamp; }
    public void setMoveTimestamp(Timestamp moveTimestamp) { this.moveTimestamp = moveTimestamp; }
}
