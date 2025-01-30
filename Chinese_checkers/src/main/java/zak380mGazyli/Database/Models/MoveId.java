package zak380mGazyli.Database.Models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the Move entity.
 */
public class MoveId implements Serializable {
    private int game;  // This should match the game_id in the Move entity
    private int turnNumber;

    /**
     * Default constructor for the MoveId class.
     */
    public MoveId() {}

    /**
     * Constructs a MoveId with the specified game ID and turn number.
     *
     * @param game the game ID
     * @param turnNumber the turn number
     */
    public MoveId(int game, int turnNumber) {
        this.game = game;
        this.turnNumber = turnNumber;
    }

    /**
     * Checks if this MoveId is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveId moveId = (MoveId) o;
        return game == moveId.game && turnNumber == moveId.turnNumber;
    }

    /**
     * Returns the hash code for this MoveId.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(game, turnNumber);
    }

    /**
     * Gets the game ID.
     *
     * @return the game ID
     */
    public int getGame() { return game; }

    /**
     * Sets the game ID.
     *
     * @param game the game ID to set
     */
    public void setGame(int game) { this.game = game; }

    /**
     * Gets the turn number.
     *
     * @return the turn number
     */
    public int getTurnNumber() { return turnNumber; }

    /**
     * Sets the turn number.
     *
     * @param turnNumber the turn number to set
     */
    public void setTurnNumber(int turnNumber) { this.turnNumber = turnNumber; }
}
