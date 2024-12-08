package zak380mGazyli.Cells;

import zak380mGazyli.Misc.Color;

public class ClassicBoardCell implements Cell {
    private String colorName = Color.WHITE;

    @Override
    public void setColor(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String getColor() {
        return this.colorName;
    }
}