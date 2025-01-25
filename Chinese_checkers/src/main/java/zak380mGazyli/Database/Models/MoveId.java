package zak380mGazyli.Database.Models;

import java.io.Serializable;
import java.util.Objects;

public class MoveId implements Serializable {
    private int game;  // This should match the game_id in the Move entity
    private int turnNumber;

    // Default constructor
    public MoveId() {}

    public MoveId(int game, int turnNumber) {
        this.game = game;
        this.turnNumber = turnNumber;
    }

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveId moveId = (MoveId) o;
        return game == moveId.game && turnNumber == moveId.turnNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, turnNumber);
    }

    // Getters and setters
    public int getGame() { return game; }
    public void setGame(int game) { this.game = game; }

    public int getTurnNumber() { return turnNumber; }
    public void setTurnNumber(int turnNumber) { this.turnNumber = turnNumber; }
}

