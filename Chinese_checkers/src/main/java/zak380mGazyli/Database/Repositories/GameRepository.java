package zak380mGazyli.Database.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zak380mGazyli.Database.Models.Game;

/**
 * Repository interface for accessing Game entities from the database.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface GameRepository extends JpaRepository<Game, Integer> {
}
