package zak380mGazyli.Database.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zak380mGazyli.Database.Models.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
}
