package zak380mGazyli.Boards;

public interface Board {
    void initializeBoard();  // Initialize the board for a new game
    String[][] getBoard();   // Return the current state of the board
    void updateBoard(int[] startPos, int[] endPos);  // Update the board based on a move
    void displayBoard();  // Print the board (for testing/debugging)
}

