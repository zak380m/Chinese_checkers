package zak380mGazyli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zak380mGazyli.Displays.Display;
import zak380mGazyli.Displays.GUI.GUIApp;
import zak380mGazyli.Displays.GUI.GUIDisplay;

public class GUITest {
    Display display;

    @BeforeEach
    public void setUp() {
        display = new GUIDisplay();
    }

    @Test
    public void testMessageDisplay() {
        GUIApp.displayMessage("Test message");
    }
}
