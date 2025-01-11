package zak380mGazyli.Displays.GUI.Misc;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class ColorWrapper {
    private static final Map<String, Color> colorMap = new HashMap<>();
    private static final Map<String, Color> fancyColorMap = new HashMap<>();

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

    static {
        fancyColorMap.put("WHITE", Color.WHITE);
        fancyColorMap.put("RED", Color.web("#EE4266"));
        fancyColorMap.put("GREEN", Color.web("#6EBA2A"));
        fancyColorMap.put("YELLOW", Color.web("#FFD23F"));
        fancyColorMap.put("BLUE", Color.web("#2978A0"));
        fancyColorMap.put("MAGENTA", Color.web("#540D6E"));
        fancyColorMap.put("CYAN", Color.web("#56EEF4"));
        fancyColorMap.put("GREY", Color.GREY);
    }

    public static Color getJavaFXColor(String colorName) {
//        return colorMap.getOrDefault(colorName, Color.BLACK);
        return fancyColorMap.getOrDefault(colorName, Color.BLACK);
    }
}
