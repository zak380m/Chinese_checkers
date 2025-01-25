package zak380mGazyli.Database.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zak380mGazyli.Database.Models.Move;
import zak380mGazyli.Database.Models.MoveId;

@Repository
public interface MoveRepository extends JpaRepository<Move, MoveId> {
}
