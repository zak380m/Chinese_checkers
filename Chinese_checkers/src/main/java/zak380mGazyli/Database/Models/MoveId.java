package zak380mGazyli.Database.Models;

import java.io.Serializable;
import java.util.Objects;

public class MoveId implements Serializable {

    private int gameId;
    private int turnNumber;

    // Constructors, Getters, Setters, equals, and hashCode
    public MoveId() {}

    public MoveId(int gameId, int turnNumber) {
        this.gameId = gameId;
        this.turnNumber = turnNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveId moveId = (MoveId) o;
        return gameId == moveId.gameId && turnNumber == moveId.turnNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, turnNumber);
    }
}
