package zak380mGazyli.Database.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zak380mGazyli.Database.Models.Game;
import zak380mGazyli.Database.Repositories.GameRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Create a new game
    public Game createGame(String gameMode, String board) {
        Game game = new Game(gameMode, board);
        return gameRepository.save(game); // Saves the new game to the database
    }

    // Find a game by its ID
    public Optional<Game> findGameById(int id) {
        return gameRepository.findById(id); // Looks up the game by its primary key
    }

    // Get all games
    public List<Game> getAllGames() {
        return gameRepository.findAll(); // Retrieves all games from the database
    }

    // Delete a game by its ID
    public void deleteGameById(int id) {
        gameRepository.deleteById(id); // Deletes the game with the specified ID
    }
}
