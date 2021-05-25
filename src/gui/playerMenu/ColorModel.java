package gui.playerMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ColorModel {
    private static ArrayList<Color> colors = new ArrayList<>(Arrays.asList(
            new PlayerColor(0,0,0, "black"),
            new PlayerColor(255, 255, 255, "white"),
            new PlayerColor(255,0,0, "red"),
            new PlayerColor(0, 0, 255, "blue"),
            new PlayerColor(255, 255, 0, "yellow"),
            new PlayerColor(20, 255,0, "green")
    ));

//            new Color(0,0,0),
//            new Color(255, 255, 255),
//            new Color(255,0,0),
//            new Color(0, 0, 255),
//            new Color(255, 255, 0),
//            new Color(20, 255,0)

    private static final ArrayList<Color> availableColors = new ArrayList<>(colors);

    public static Collection<Color> getAvailableColors() {
        return availableColors;
    }

    public static void chooseNewColor(Color newColor, Color oldColor) {
        if (availableColors.contains(newColor)
                && colors.contains(oldColor)
                && !newColor.equals(oldColor)) {

            availableColors.add(oldColor);
            availableColors.remove(newColor);
        }
    }
}
