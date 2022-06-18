package dev.nikomaru.horseinfonoticer.utils;

import static dev.nikomaru.horseinfonoticer.utils.HorseInfoStats.calcJumpHeight;
import static dev.nikomaru.horseinfonoticer.utils.HorseInfoStats.calcSpeed;

import java.util.ArrayList;
import java.util.List;

public class HorseInfoFormat {

    public static List<String> formatHorseStats(double health, double maxHealth, double speed, double jumpStrength) {
        List<String> stringArray = new ArrayList<>();
        stringArray.add(String.format("HP: %.2f/%.2f", health, maxHealth));
        stringArray.add(String.format("SP: %.4f [%.2f(m/s)]", speed, calcSpeed(speed)));
        stringArray.add(String.format("JP: %.4f [%.2f(m)]", jumpStrength, calcJumpHeight(jumpStrength)));
        return stringArray;
    }

    public static String formatHorseNameWithRank(String name, String evaluateRank) {
        return name + " [" + evaluateRank + "]";
    }
}
