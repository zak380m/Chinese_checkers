package zak380mGazyli.Misc;

import java.util.HashMap;
import java.util.Map;

public class Color {
    public static final String WHITE = "WHITE";
    public static final String RED = "RED";
    public static final String GREEN = "GREEN";
    public static final String YELLOW = "YELLOW";
    public static final String BLUE = "BLUE";
    public static final String MAGENTA = "MAGENTA";
    public static final String CYAN = "CYAN";
    public static final String GREY = "GREY";

    private static final Map<String, String> colorMap = new HashMap<>();

    static {
        colorMap.put(WHITE, "\u001B[0m");
        colorMap.put(RED, "\u001B[31m");
        colorMap.put(GREEN, "\u001B[32m");
        colorMap.put(YELLOW, "\u001B[33m");
        colorMap.put(BLUE, "\u001B[34m");
        colorMap.put(MAGENTA, "\u001B[35m");
        colorMap.put(CYAN, "\u001B[36m");
        colorMap.put(GREY, "\u001B[90m");
    }

    public static String getEscapeSequence(String colorName) {
        return colorMap.getOrDefault(colorName, "\u001B[0m");
    }
}