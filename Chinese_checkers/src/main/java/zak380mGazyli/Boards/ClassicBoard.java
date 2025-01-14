package zak380mGazyli.Boards;

import zak380mGazyli.Misc.Cell;
import zak380mGazyli.Misc.Color;

import java.util.HashMap;
import java.util.Map;

public class ClassicBoard implements Board {
    private final String symbol = ".";
    private final Cell[][] cells = {
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)},
        {new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(symbol, Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE), new Cell(" ", Color.WHITE)}
    };

    private final Map<Integer, int[][]> triangles = new HashMap<>();

    public ClassicBoard() {
        triangles.put(1, new int[][]{{0, 12}, {1, 11}, {1, 13}, {2, 10}, {2, 12}, {2, 14}, {3, 9}, {3, 11}, {3, 13}, {3, 15}});
        triangles.put(2, new int[][]{{4, 18}, {4, 20}, {4, 22}, {4, 24}, {5, 19}, {5, 21}, {5, 23},  {6, 20}, {6, 22}, {7, 21}});
        triangles.put(3, new int[][]{{12, 18}, {12, 20}, {12, 22}, {12, 24}, {11, 19}, {11, 21}, {11, 23},  {10, 20}, {10, 22}, {9, 21}});
        triangles.put(4, new int[][]{{16, 12}, {15, 11}, {15, 13}, {14, 10}, {14, 12}, {14, 14}, {13, 9}, {13, 11}, {13, 13}, {13, 15}});
        triangles.put(5, new int[][]{{12, 0}, {12, 2}, {12, 4}, {12, 6}, {11, 1}, {11, 3}, {11, 5},  {10, 2}, {10, 4}, {9, 3}});
        triangles.put(6, new int[][]{{4, 0}, {4, 2}, {4, 4}, {4, 6}, {5, 1}, {5, 3}, {5, 5},  {6, 2}, {6, 4}, {7, 3}});
    }

    @Override
    public Cell[][] getBoard() {
        return cells;
    }

    @Override
    public int[][] getNeighbours() {
        return new int[][]{{0, 2}, {0, -2}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}};
    }

    @Override
    public void updateBoard(int startX, int startY, int endX, int endY) {
        if (cells[startY][startX].getSymbol().equals(" ") || cells[endY][endX].getSymbol().equals(" ")) {
            System.out.println("Invalid coordinates in updateBoard");
            return;
        }
        Cell swap = cells[startY][startX];
        cells[startY][startX] = cells[endY][endX];
        cells[endY][endX] = swap;
    }

    @Override
    public void displayBoard() {
        // Displaying the board in console for debugging
        for (Cell[] cell : cells) {
            for (int j = 0; j < cells[0].length; j++) {
                System.out.print(Color.getEscapeSequence(cell[j].getColor()) + cell[j].getSymbol());
            }
            System.out.print("\n");
        }
    }

    @Override
    public int[][] getStartArea(int area_num) {
        return triangles.get(area_num);
    }

    public void colorTriangle(int triangle_num, String color) {
        for (int[] cord : triangles.get(triangle_num)) {
            cells[cord[0]][cord[1]].setColor(color);
            cells[cord[0]][cord[1]].setSymbol("O");
        }
    }

    public void colorTriangle2P(int triangle_num, String color) {
        triangles.put(1, new int[][]{{0, 12}, {1, 11}, {1, 13}, {2, 10}, {2, 12}, {2, 14}, {3, 9}, {3, 11}, {3, 13}, {3, 15}, {4, 8}, {4, 10}, {4, 12}, {4, 14}, {4, 16}});
        triangles.put(4, new int[][]{{16, 12}, {15, 11}, {15, 13}, {14, 10}, {14, 12}, {14, 14}, {13, 9}, {13, 11}, {13, 13}, {13, 15}, {12, 8}, {12, 10}, {12, 12}, {12, 14}, {12, 16}});
        for (int[] cord : triangles.get(triangle_num)) {
            cells[cord[0]][cord[1]].setColor(color);
            cells[cord[0]][cord[1]].setSymbol("O");
        }
    }
}

