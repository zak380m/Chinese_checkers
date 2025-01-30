package zak380mGazyli.Database.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * Represents a move entity in the database.
 */
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

    /**
     * Default constructor for the Move class.
     */
    public Move() {}

    /**
     * Constructs a Move with the specified parameters.
     *
     * @param game the game associated with the move
     * @param turnNumber the turn number of the move
     * @param startX the starting x-coordinate of the move
     * @param startY the starting y-coordinate of the move
     * @param endX the ending x-coordinate of the move
     * @param endY the ending y-coordinate of the move
     * @param isPass whether the move is a pass
     */
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

    /**
     * Gets the game associated with the move.
     *
     * @return the game
     */
    public Game getGame() { return game; }

    /**
     * Sets the game associated with the move.
     *
     * @param game the game to set
     */
    public void setGame(Game game) { this.game = game; }

    /**
     * Gets the turn number of the move.
     *
     * @return the turn number
     */
    public int getTurnNumber() { return turnNumber; }

    /**
     * Sets the turn number of the move.
     *
     * @param turnNumber the turn number to set
     */
    public void setTurnNumber(int turnNumber) { this.turnNumber = turnNumber; }

    /**
     * Gets the starting x-coordinate of the move.
     *
     * @return the starting x-coordinate
     */
    public int getStartX() { return startX; }

    /**
     * Sets the starting x-coordinate of the move.
     *
     * @param startX the starting x-coordinate to set
     */
    public void setStartX(int startX) { this.startX = startX; }

    /**
     * Gets the starting y-coordinate of the move.
     *
     * @return the starting y-coordinate
     */
    public int getStartY() { return startY; }

    /**
     * Sets the starting y-coordinate of the move.
     *
     * @param startY the starting y-coordinate to set
     */
    public void setStartY(int startY) { this.startY = startY; }

    /**
     * Gets the ending x-coordinate of the move.
     *
     * @return the ending x-coordinate
     */
    public int getEndX() { return endX; }

    /**
     * Sets the ending x-coordinate of the move.
     *
     * @param endX the ending x-coordinate to set
     */
    public void setEndX(int endX) { this.endX = endX; }

    /**
     * Gets the ending y-coordinate of the move.
     *
     * @return the ending y-coordinate
     */
    public int getEndY() { return endY; }

    /**
     * Sets the ending y-coordinate of the move.
     *
     * @param endY the ending y-coordinate to set
     */
    public void setEndY(int endY) { this.endY = endY; }

    /**
     * Checks if the move is a pass.
     *
     * @return true if the move is a pass, false otherwise
     */
    public boolean isPass() { return isPass; }

    /**
     * Sets whether the move is a pass.
     *
     * @param pass true if the move is a pass, false otherwise
     */
    public void setPass(boolean pass) { isPass = pass; }

    /**
     * Gets the timestamp of the move.
     *
     * @return the move timestamp
     */
    public Timestamp getMoveTimestamp() { return moveTimestamp; }

    /**
     * Sets the timestamp of the move.
     *
     * @param moveTimestamp the move timestamp to set
     */
    public void setMoveTimestamp(Timestamp moveTimestamp) { this.moveTimestamp = moveTimestamp; }
}
