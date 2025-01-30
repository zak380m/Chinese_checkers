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
import zak380mGazyli.PlayersHandling.GameRoom;

/**
 * The Mediator class acts as an intermediary between different components of the game,
 * handling game creation, moves, and game room management.
 */
@Service
public class Mediator {

    private final GameService gameService;
    private final MoveService moveService;
    private final GameBuilder gameBuilder = new GameBuilder();
    private Server server;

    /**
     * Constructs a Mediator instance with the specified game and move services.
     *
     * @param gameService The service for managing games.
     * @param moveService The service for managing moves.
     */
    public Mediator(GameService gameService, MoveService moveService) {
        this.gameService = gameService;
        this.moveService = moveService;
    }

    /**
     * Sets the server instance for the mediator.
     *
     * @param server The server instance.
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * Adds a game to the database.
     *
     * @param game The game to add.
     */
    public void addGame(Game game) {
        gameService.createGame(game);
    }

    /**
     * Adds a move to the database.
     *
     * @param move The move to add.
     */
    public void addMove(Move move) {
        moveService.createMove(move);
    }

    /**
     * Deletes a game from the database.
     *
     * @param gameID The ID of the game to delete.
     */
    public void deleteGame(int gameID) {
        gameService.deleteGame(gameID);
    }

    /**
     * Retrieves the list of moves for a given game.
     *
     * @param game The game for which to retrieve moves.
     * @return The list of moves for the game.
     */
    private List<Move> getMoves(Game game) {
        return moveService.getMovesByGameId(game.getId());
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param gameId The ID of the game to retrieve.
     * @return The game instance.
     */
    private Game getGame(int gameId) {
        return gameService.getGame(gameId);
    }

    /**
     * Loads a game room with the specified game ID and password.
     *
     * @param GameId The ID of the game to load.
     * @param password The password for the game room.
     * @return The loaded GameRoom instance.
     */
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
        return server.createGameRoom(gamemode, board, password, game.getPlayerNumber(), 0, game);
    }

    /**
     * Replays the game moves in the specified game room.
     *
     * @param room The game room in which to replay the moves.
     */
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
