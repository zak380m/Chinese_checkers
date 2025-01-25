package zak380mGazyli.Database.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import zak380mGazyli.Database.Models.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
