package zak380mGazyli.Displays.GUI.Misc;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class ColorWrapper {
    private static final Map<String, Color> colorMap = new HashMap<>();

    static {
        colorMap.put("WHITE", Color.WHITE);
        colorMap.put("RED", Color.RED);
        colorMap.put("GREEN", Color.GREEN);
        colorMap.put("YELLOW", Color.YELLOW);
        colorMap.put("BLUE", Color.BLUE);
        colorMap.put("MAGENTA", Color.MAGENTA);
        colorMap.put("CYAN", Color.CYAN);
        colorMap.put("GREY", Color.GREY);
    }

    public static Color getJavaFXColor(String colorName) {
        return colorMap.getOrDefault(colorName, Color.BLACK);
    }
}
