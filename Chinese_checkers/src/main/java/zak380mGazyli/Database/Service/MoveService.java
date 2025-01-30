package zak380mGazyli.Database.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zak380mGazyli.Database.Models.Move;
import zak380mGazyli.Database.Models.MoveId;
import zak380mGazyli.Database.Repositories.MoveRepository;

import java.util.List;

/**
 * Service class for managing Move entities.
 */
@Service
public class MoveService {

    @Autowired
    private MoveRepository moveRepository;

    /**
     * Retrieves all moves from the database.
     *
     * @return a list of all moves
     */
    public List<Move> getAllMoves() {
        return moveRepository.findAll();
    }

    /**
     * Retrieves a move by its composite ID.
     *
     * @param id the composite ID of the move
     * @return the move with the specified ID, or null if not found
     */
    public Move getGame(MoveId id) {
        return moveRepository.findById(id).orElse(null);
    }

    /**
     * Creates a new move in the database.
     *
     * @param move the move to create
     * @return the created move
     */
    public Move createMove(Move move) {
        return moveRepository.save(move);
    }

    /**
     * Deletes a move by its composite ID.
     *
     * @param id the composite ID of the move to delete
     */
    public void deleteMove(MoveId id) {
        moveRepository.deleteById(id);
    }

    /**
     * Finds all moves associated with a specific game ID.
     *
     * @param gameId the ID of the game
     * @return a list of moves associated with the specified game ID
     */
    public List<Move> getMovesByGameId(int gameId) {
        return moveRepository.findByGameId(gameId);
    }
}
