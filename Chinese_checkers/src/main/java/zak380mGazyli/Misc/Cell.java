package zak380mGazyli.Misc;

/**
 * The Cell class represents a cell in the game board with a symbol and a color.
 */
public class Cell {
    private String cellSymbol;
    private String cellColor;

    /**
     * Constructs a Cell instance with the specified symbol and color.
     *
     * @param cellSymbol The symbol of the cell.
     * @param cellColor The color of the cell.
     */
    public Cell(String cellSymbol, String cellColor) {
        this.cellSymbol = cellSymbol;
        this.cellColor = cellColor;
    }

    /**
     * Sets the symbol of the cell.
     *
     * @param cellSymbol The new symbol of the cell.
     */
    public void setSymbol(String cellSymbol) {
        this.cellSymbol = cellSymbol;
    }

    /**
     * Gets the symbol of the cell.
     *
     * @return The symbol of the cell.
     */
    public String getSymbol() {
        return this.cellSymbol;
    }

    /**
     * Sets the color of the cell.
     *
     * @param cellColor The new color of the cell.
     */
    public void setColor(String cellColor) {
        this.cellColor = cellColor;
    }

    /**
     * Gets the color of the cell.
     *
     * @return The color of the cell.
     */
    public String getColor() {
        return this.cellColor;
    }
}
