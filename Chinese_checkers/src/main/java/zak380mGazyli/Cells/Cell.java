package zak380mGazyli.Cells;

import zak380mGazyli.Misc.Color;

public class Cell {
    private String colorName = Color.WHITE;

    public void setColor(String colorName) {
        this.colorName = colorName;
    }

    public String getColor() {
        return this.colorName;
    }
}