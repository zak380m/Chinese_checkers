package zak380mGazyli.Database.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zak380mGazyli.Database.Models.Move;
import zak380mGazyli.Database.Models.MoveId;
import zak380mGazyli.Database.Repositories.MoveRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MoveService {

    private final MoveRepository moveRepository;

    @Autowired
    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    // Save a move to the database
    public Move saveMove(Move move) {
        return moveRepository.save(move); // Save or update the move in the database
    }

    // Find a move by its composite ID
    public Optional<Move> findMoveById(MoveId moveId) {
        return moveRepository.findById(moveId); // Look up a move by its composite ID
    }

    // Get all moves
    public List<Move> getAllMoves() {
        return moveRepository.findAll(); // Retrieve all moves from the database
    }

    // Delete a move by its composite ID
    public void deleteMoveById(MoveId moveId) {
        moveRepository.deleteById(moveId); // Delete the move with the given composite ID
    }

    // Additional method to find moves by game
    public List<Move> findMovesByGameId(int gameId) {
        return moveRepository.findAll().stream()
                .filter(move -> move.getGame().getId() == gameId)
                .toList(); // Find moves for a specific game
    }
}
