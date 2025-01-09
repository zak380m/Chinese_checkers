package zak380mGazyli.Boards;

import zak380mGazyli.Misc.Cell;
import zak380mGazyli.Misc.Color;

import java.util.HashMap;
import java.util.Map;

public class ClassicBoard implements Board {
    private final int x_size = 17;
    private final int y_size = 25;
    private final String symbol = ".";
//    private final Cell[][] cells = new Cell[x_size][y_size];
    private final Cell[][] cells = {
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)}
};

    public ClassicBoard() {
    }

    @Override
    public Cell[][] getBoard() {
        return cells;
    }

    @Override
    public int getCols() {
        return x_size;
    }
    
    @Override
    public int getRows() {
        return y_size;
    }

    @Override
    public void updateBoard(int startX, int startY, int endX, int endY) {
        Cell swap = cells[startX][startY];
        cells[startX][startY] = cells[endX][endY];
        cells[endX][endY] = swap;
    }

    @Override
    public void displayBoard() {
        // Displaying the board in console for debugging
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                System.out.print(Color.getEscapeSequence(cells[i][j].getColor()) + cells[i][j].getSymbol());
            }
            System.out.print("\n");
        }
    }

    public void colorTriangle(int triangle_num, String color) {
        Map<Integer, Cell[]> triangles = new HashMap<>();
        triangles.put(1, new Cell[]{cells[0][12], cells[1][11], cells[1][13], cells[2][10], cells[2][12], cells[2][14], cells[3][9], cells[3][11], cells[3][13], cells[3][15]});
        triangles.put(2, new Cell[]{cells[4][18], cells[4][20], cells[4][22], cells[4][24], cells[5][19], cells[5][21], cells[5][23],  cells[6][20], cells[6][22], cells[7][21]});
        triangles.put(3, new Cell[]{cells[12][18], cells[12][20], cells[12][22], cells[12][24], cells[11][19], cells[11][21], cells[11][23],  cells[10][20], cells[10][22], cells[9][21]});
        triangles.put(4, new Cell[]{cells[16][12], cells[15][11], cells[15][13], cells[14][10], cells[14][12], cells[14][14], cells[13][9], cells[13][11], cells[13][13], cells[13][15]});
        triangles.put(5, new Cell[]{cells[12][0], cells[12][2], cells[12][4], cells[12][6], cells[11][1], cells[11][3], cells[11][5],  cells[10][2], cells[10][4], cells[9][3]});
        triangles.put(6, new Cell[]{cells[4][0], cells[4][2], cells[4][4], cells[4][6], cells[5][1], cells[5][3], cells[5][5],  cells[6][2], cells[6][4], cells[7][3]});

        for (Cell cell : triangles.get(triangle_num)) {
            cell.setSymbol("O");
            cell.setColor(color);
        }
    }

    public void colorTriangle2P(int triangle_num, String color) {
        Map<Integer, Cell[]> triangles = new HashMap<>();
        triangles.put(1, new Cell[]{cells[0][12], cells[1][11], cells[1][13], cells[2][10], cells[2][12], cells[2][14], cells[3][9], cells[3][11], cells[3][13], cells[3][15], cells[4][8], cells[4][10], cells[4][12], cells[4][14], cells[4][16]});
        triangles.put(4, new Cell[]{cells[16][12], cells[15][11], cells[15][13], cells[14][10], cells[14][12], cells[14][14], cells[13][9], cells[13][11], cells[13][13], cells[13][15], cells[12][8], cells[12][10], cells[12][12], cells[12][14], cells[12][16]});
        for (Cell cell : triangles.get(triangle_num)) {
            cell.setSymbol("O");
            cell.setColor(color);
        }
    }
}

