package zak380mGazyli;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zak380mGazyli.Database.Models.*;
import zak380mGazyli.Database.Service.*;
import zak380mGazyli.PlayersHandling.GameRoom;

@Service
public class Mediator {

    private final GameService gameService;
    private final MoveService moveService;

    public Mediator(GameService gameService, MoveService moveService) { 
        this.gameService = gameService;
        this.moveService = moveService;
    }

    public void addGame(Game game) {
        gameService.createGame(game);
    }

    public void addMove(Move move) {
        moveService.createMove(move);
    }

    public void deleteGame(int gameID) {
        gameService.deleteGame(gameID);
    }

    private List<Move> getMoves(Game game) {
        return moveService.getMovesByGameId(game.getId());
    }

    private Game getGame(int gameId) {
        return gameService.getGame(gameId);
    }
    
    public GameRoom loadRoom(int GameId) {
        return null;
    }
}
