package zak380mGazyli.Database.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zak380mGazyli.Database.Models.Move;
import zak380mGazyli.Database.Models.MoveId;

/**
 * Repository interface for accessing Move entities from the database.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface MoveRepository extends JpaRepository<Move, MoveId> {

    /**
     * Finds all moves associated with a specific game ID.
     *
     * @param gameId the ID of the game
     * @return a list of moves associated with the specified game ID
     */
    List<Move> findByGameId(int gameId);
}
