package zak380mGazyli;

import org.junit.jupiter.api.Test;

import zak380mGazyli.Boards.Board;
import zak380mGazyli.Builders.BoardBuilders.BoardBuilder;
import zak380mGazyli.Builders.BoardBuilders.ClassicBoardBuilder;
import zak380mGazyli.Gamemodes.SuperGamemode;
import zak380mGazyli.Gamemodes.Gamemode;

public class SuperGamemodeTest {

    @Test
    public void testPlayerSetup() {
        Gamemode gamemode = new SuperGamemode();
        for (int player_count : new int[]{2,3,4,6}) {
            BoardBuilder bb = new ClassicBoardBuilder();
            bb.buildBoard(player_count);
            Board board = bb.getBoard();
            board.displayBoard();
            gamemode.setNumberOfPlayers(player_count, board);
        }
    }

    @Test
    public void testValidateMove() {
        Gamemode gamemode = new SuperGamemode();
        for (int player_count : new int[]{2,3,4,6}) {
            BoardBuilder bb = new ClassicBoardBuilder();
            bb.buildBoard(player_count);
            Board board = bb.getBoard();
            board.displayBoard();
            gamemode.setNumberOfPlayers(player_count, board);
            System.out.println(gamemode.validateMove(0, 12, 4, 8, board));
        }
    }
}
