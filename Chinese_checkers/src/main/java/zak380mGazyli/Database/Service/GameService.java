package zak380mGazyli.Database.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zak380mGazyli.Database.Models.Game;
import zak380mGazyli.Database.Repositories.GameRepository;

import java.util.List;

/**
 * Service class for managing Game entities.
 */
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Retrieves all games from the database.
     *
     * @return a list of all games
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param id the ID of the game
     * @return the game with the specified ID, or null if not found
     */
    public Game getGame(int id) {
        return gameRepository.findById(id).orElse(null);
    }

    /**
     * Creates a new game in the database.
     *
     * @param game the game to create
     * @return the created game
     */
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    /**
     * Deletes a game by its ID.
     *
     * @param id the ID of the game to delete
     */
    public void deleteGame(int id) {
        gameRepository.deleteById(id);
    }
}
