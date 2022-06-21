package dev.nikomaru.horseinfonoticer.utils;

import dev.nikomaru.horseinfonoticer.HorseInfoNoticer;

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
        if (HorseInfoNoticer.getMode() == 1) {
            return speedBaseRank(paramSpeed);
        } else if (HorseInfoNoticer.getMode() == -1) {
            return jumpBaseRank(jumpHeight);
        }
        var horseEvaluate = calcEvaluateValue(paramSpeed, calcJumpHeight(jumpHeight));

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

    public static String speedBaseRank(double paramSpeed) {
        var speed = calcSpeed(paramSpeed);
        var rank = "G";
        if (speed >= 13.6) {
            var list = new String[]{"LEGEND", "S++", "S+", "S", "A++", "A+"};

            for (var i = 0; i < list.length; i++) {
                if (speed > 14.1 - (i * 0.1)) {
                    rank = list[i];
                    break;
                }
            }

        } else {
            var list = new String[]{"A", "B++", "B+", "B", "C++", "C+", "C", "D", "E", "F", "G"};

            for (var i = 0; i < list.length; i++) {
                if (speed > 13.4 - (i * 0.2)) {
                    rank = list[i];
                    break;
                }
            }

        }
        return rank;
    }

    public static String jumpBaseRank(double jumpHeight) {
        var jump = calcJumpHeight(jumpHeight);
        var rank = "G";
        var list = new String[]{"LEGEND", "S++", "S+", "S", "A++", "A+", "A", "B++", "B+", "B", "C++", "C+", "C", "D", "E", "F", "G"};

        for (var i = 0; i < list.length; i++) {
            if (jump >= 5.0 - (i * 0.25)) {
                rank = list[i];
                break;
            }
        }


        return rank;
    }


    public static Color calcEvaluateRankColor(String rankString) {

        return switch (rankString) {
            case "B", "B+" -> new Color(0x55, 0x55, 0xFF);
            case "B++" -> new Color(0x00, 0xAA, 0xFF);
            case "A" -> new Color(0x55, 0xFF, 0xFF);
            case "A+" -> new Color(0x55, 0xFF, 0x55);
            case "A++" -> new Color(0xFF, 0xFF, 0x55);
            case "S" -> new Color(0xFF, 0xAA, 0x00);
            case "S+" -> new Color(0xFF, 0x55, 0x55);
            case "S++" -> new Color(0xFF, 0x55, 0xFF);
            case "LEGEND" -> new Color(0xFF, 0xCC, 0xFF);
            default -> Color.BLACK;
        };
    }
}
