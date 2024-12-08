package zak380mGazyli.Builders;

import zak380mGazyli.Boards.Board;

public interface BoardBuilder {
    void buildBoard(int players);
    Board getBoard();
}
