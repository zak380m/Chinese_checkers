package zak380mGazyli.Database.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class Move {

    @EmbeddedId
    private MoveId id;

    @ManyToOne
    @MapsId("gameId")  // Maps gameId from MoveId to game_id in Game entity
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "start_x")
    private Integer startX;

    @Column(name = "start_y")
    private Integer startY;

    @Column(name = "end_x")
    private Integer endX;

    @Column(name = "end_y")
    private Integer endY;

    @Column(name = "is_pass", nullable = false)
    private boolean isPass;

    @Column(name = "move_timestamp", nullable = false, updatable = false)
    private Timestamp moveTimestamp;

    // Constructors, Getters, Setters, etc.

    public Move() {}

    public Move(Game game, int turnNumber, Integer startX, Integer startY, Integer endX, Integer endY, boolean isPass) {
        this.id = new MoveId(game.getId(), turnNumber); // Set composite key
        this.game = game;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.isPass = isPass;
        this.moveTimestamp = new Timestamp(System.currentTimeMillis()); // Set timestamp
    }

    // Getters and Setters

    public MoveId getId() {
        return id;
    }

    public void setId(MoveId id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getStartX() {
        return startX;
    }

    public void setStartX(Integer startX) {
        this.startX = startX;
    }

    public Integer getStartY() {
        return startY;
    }

    public void setStartY(Integer startY) {
        this.startY = startY;
    }

    public Integer getEndX() {
        return endX;
    }

    public void setEndX(Integer endX) {
        this.endX = endX;
    }

    public Integer getEndY() {
        return endY;
    }

    public void setEndY(Integer endY) {
        this.endY = endY;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public Timestamp getMoveTimestamp() {
        return moveTimestamp;
    }

    public void setMoveTimestamp(Timestamp moveTimestamp) {
        this.moveTimestamp = moveTimestamp;
    }
}
