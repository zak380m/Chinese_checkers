package zak380mGazyli.Database.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zak380mGazyli.Database.Models.Move;
import zak380mGazyli.Database.Models.MoveId;
import zak380mGazyli.Database.Repositories.MoveRepository;

import java.util.List;

@Service
public class MoveService {

    @Autowired
    private MoveRepository moveRepository;

    public List<Move> getAllMoves() {
        return moveRepository.findAll();
    }

    public Move getGame(MoveId id) {
        return moveRepository.findById(id).orElse(null);
    }

    public Move createMove(Move move) {
        return moveRepository.save(move);
    }

    public void deleteMove(MoveId id) {
        moveRepository.deleteById(id);
    }

    public List<Move> getMovesByGameId(int gameId) {
        return moveRepository.findByGameId(gameId);
    }
}
