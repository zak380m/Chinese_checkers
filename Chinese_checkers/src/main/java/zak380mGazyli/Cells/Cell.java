package zak380mGazyli.Cells;

import zak380mGazyli.Misc.Color;

public class Cell {
    private String cellSymbol;
    private String cellColor;

    public Cell(String cellSymbol, String cellColor) {
        this.cellSymbol = cellSymbol;
        this.cellColor = cellColor;
    }

    public void setSymbol(String cellSymbol) {
        this.cellSymbol = cellSymbol;
    }

    public String getSymbol() {
        return this.cellSymbol;
    }

    public void setColor(String cellColor) {
        this.cellColor = cellColor;
    }

    public String getColor() {
        return this.cellColor;
    }
}