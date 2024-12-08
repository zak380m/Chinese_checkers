package zak380mGazyli.Boards;

public class ClassicBoard implements Board {
    private String[][] board;

    @Override
    public void initializeBoard() {
        board = new String[10][10];  // Simple 10x10 grid for demonstration
        // Initialize the board (you can add real setup logic here)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = ".";
            }
        }
        // Example pieces initialization (start positions)
        board[0][0] = "X";  // Player 1
        board[9][9] = "O";  // Player 2
    }

    @Override
    public String[][] getBoard() {
        return board;
    }

    @Override
    public void updateBoard(int[] startPos, int[] endPos) {
        board[endPos[0]][endPos[1]] = board[startPos[0]][startPos[1]];
        board[startPos[0]][startPos[1]] = ".";
    }

    @Override
    public void displayBoard() {
        for (String[] row : board) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}

