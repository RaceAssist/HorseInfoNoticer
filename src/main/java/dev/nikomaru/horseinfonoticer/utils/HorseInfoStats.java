package dev.nikomaru.horseinfonoticer.utils;

import java.awt.Color;

public class HorseInfoStats {

    public static double calcJumpHeight(double strength) {
        return Math.pow(strength, 1.7) * 5.293;
    }

    public static double calcSpeed(double speed) {
        return speed * 42.162962963D;
    }

    public static double calcEvaluateValue(double paramSpeed, double jumpHeight) {
        var jumpRating = Math.floor(jumpHeight * 2.0D) / (2.0D * 5.0D);

        final var speedHeavy = 10.0D;
        final var heightHeavy = 1.0D;

        final var valueMax = 0.3375D * speedHeavy + heightHeavy;
        var value = (paramSpeed * speedHeavy) + jumpRating * heightHeavy;
        return value / valueMax;
    }

    public static String calcEvaluateRankString(double paramSpeed, double jumpHeight) {
        var horseEvaluate = calcEvaluateValue(paramSpeed, jumpHeight);

        final var rankString = new String[]{
                "G", "G", "G",
                "F", "F", "F",
                "E", "E", "E",
                "D", "D", "D",
                "C", "C+", "C++",
                "B", "B+", "B++",
                "A", "A+", "A++",
                "S", "S+", "S++",
                "LEGEND"
        };

        var rate = horseEvaluate * 2.0D - 1.0;

        var pt = (int) (rate * rankString.length);
        if (pt >= rankString.length) {
            return rankString[rankString.length - 1];
        }
        if (pt < 0) {
            return rankString[0];
        }

        return rankString[pt];
    }

    public static Color calcEvaluateRankColor(double paramSpeed, double jumpHeight) {
        var horseEvaluate = calcEvaluateValue(paramSpeed, jumpHeight);

        final var rankColor = new Color[]{
                Color.BLACK, Color.BLACK, Color.BLACK,
                Color.BLACK, Color.BLACK, Color.BLACK,
                Color.BLACK, Color.BLACK, Color.BLACK,
                Color.BLACK, Color.BLACK, Color.BLACK,
                Color.BLACK, Color.BLACK, Color.BLACK,
                new Color(0x55, 0x55, 0xFF), new Color(0x55, 0x55, 0xFF), new Color(0x00, 0xAA, 0xFF),
                new Color(0x55, 0xFF, 0xFF), new Color(0x55, 0xFF, 0x55), new Color(0xFF, 0xFF, 0x55),
                new Color(0xFF, 0xAA, 0x00), new Color(0xFF, 0x55, 0x55), new Color(0xFF, 0x55, 0xFF),
                new Color(0xFF, 0xCC, 0xFF)
        };
        var rate = horseEvaluate * 2.0D - 1.0;
        var pt = (int) (rate * rankColor.length);
        if (pt >= rankColor.length) {
            return rankColor[rankColor.length - 1];
        }
        if (pt < 0) {
            return rankColor[0];
        }
        return rankColor[pt];
    }

}
