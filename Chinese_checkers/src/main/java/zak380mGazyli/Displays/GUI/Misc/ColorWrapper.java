package zak380mGazyli.Displays.GUI.Misc;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * The ColorWrapper class provides a mapping between color names and JavaFX Color objects.
 * It includes two maps: one for standard colors and one for fancy colors.
 */
public class ColorWrapper {
    private static final Map<String, Color> colorMap = new HashMap<>();
    private static final Map<String, Color> fancyColorMap = new HashMap<>();

    // Static block to initialize the standard color map
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

    // Static block to initialize the fancy color map
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

    /**
     * Gets the JavaFX Color object corresponding to the given color name.
     * If the color name is not found in the fancy color map, it defaults to black.
     *
     * @param colorName The name of the color.
     * @return The JavaFX Color object corresponding to the color name.
     */
    public static Color getJavaFXColor(String colorName) {
        // Return the color from the fancy color map or default to black if not found
        return fancyColorMap.getOrDefault(colorName, Color.BLACK);
    }
}
