package zak380mGazyli.Displays;

public class CLIDisplay implements Display {
    
    @Override
    public void displayBoard(String[][] board) {
        for (String[] row : board) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}

