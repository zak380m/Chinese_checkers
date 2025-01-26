package zak380mGazyli;

import java.util.List;

import org.springframework.stereotype.Service;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Builders.GameBuilder;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.GamemodeBuilders.GamemodeBuilder;
import zak380mGazyli.Database.Models.*;
import zak380mGazyli.Database.Service.*;
import zak380mGazyli.Gamemodes.Gamemode;
import zak380mGazyli.Misc.GameState;
import zak380mGazyli.PlayersHandling.GameRoom;

@Service
public class Mediator {

    private final GameService gameService;
    private final MoveService moveService;
    private final GameBuilder gameBuilder = new GameBuilder();
    private Server server;

    public Mediator(GameService gameService, MoveService moveService) { 
        this.gameService = gameService;
        this.moveService = moveService;
    }

    public void setServer(Server server) {
        this.server = server;
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
    
    public GameRoom loadRoom(int GameId, String password) {
        Game game = getGame(GameId);
        gameBuilder.setGamemodeName(game.getGamemode());
        gameBuilder.setBoardName(game.getBoard());
        GamemodeBuilder gamemodeBuilder = gameBuilder.getGamemodeBuilder();
        BoardBuilder boardBuilder = gameBuilder.getBoardBuilder();
        boardBuilder.buildBoard(game.getPlayerNumber());
        if(boardBuilder.getBoard() == null) {
            System.out.println("Board not supported anymore.");
            return null;
        }
        Board board = boardBuilder.getBoard();
        gamemodeBuilder.buildGamemode(game.getPlayerNumber(), board);
        if(gamemodeBuilder.getGamemode() == null) {
            System.out.println("Gamemode not supported anymore.");
            return null;
        }
        Gamemode gamemode = gamemodeBuilder.getGamemode();
        System.out.println("Gamemode is loaded succesfully.");
        return server.createGameRoom(gamemode, board, password, game.getPlayerNumber(), 0);
    }

    public void playGame(GameRoom room) {
        Game game = room.getGame();
        List<Move> moves = getMoves(game);
        for(Move move : moves) {
            if(move.isPass())
                room.getGamemode().processPass();
            else
                room.getGamemode().processMove(move.getStartX(), move.getStartY(), move.getEndX(), move.getEndY(), room.getBoard());
        }
        room.getGamemode().setTurnCount(moves.size());
    }
}
