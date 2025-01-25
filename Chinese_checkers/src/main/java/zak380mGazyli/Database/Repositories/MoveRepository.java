package zak380mGazyli.Database.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zak380mGazyli.Database.Models.Move;
import zak380mGazyli.Database.Models.MoveId;

public interface MoveRepository extends JpaRepository<Move, MoveId> {
    List<Move> findByGameId(int gameId);
}