package zak380mGazyli;

import org.junit.jupiter.api.Test;
import zak380mGazyli.Misc.Cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {
    @Test
    public void testGetSymbol() {
        Cell cell = new Cell("symbol", "color");
        assertEquals("symbol", cell.getSymbol());
    }

    @Test
    public void testGetColor() {
        Cell cell = new Cell("symbol", "color");
        assertEquals("color", cell.getColor());
    }

    @Test
    public void testSetSymbol() {
        Cell cell = new Cell("symbol", "color");
        cell.setSymbol("new_symbol");
        assertEquals("new_symbol", cell.getSymbol());
    }

    @Test
    public void testSetColor() {
        Cell cell = new Cell("symbol", "color");
        cell.setColor("new_color");
        assertEquals("new_color", cell.getColor());
    }
}
