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
        if (speed >= 14.1) {
            rank = "LEGEND";
        } else if (speed >= 14.0) {
            rank = "S++";
        } else if (speed >= 13.9) {
            rank = "S+";
        } else if (speed >= 13.8) {
            rank = "S";
        } else if (speed >= 13.7) {
            rank = "A++";
        } else if (speed >= 13.6) {
            rank = "A+";
        } else if (speed >= 13.4) {
            rank = "A";
        } else if (speed >= 13.2) {
            rank = "B++";
        } else if (speed >= 13.0) {
            rank = "B+";
        } else if (speed >= 12.8) {
            rank = "B";
        } else if (speed >= 12.6) {
            rank = "C++";
        } else if (speed >= 12.4) {
            rank = "C+";
        } else if (speed >= 12.2) {
            rank = "C";
        } else if (speed >= 12.0) {
            rank = "D";
        } else if (speed >= 11.8) {
            rank = "E";
        } else if (speed >= 11.6) {
            rank = "F";
        } else if (speed >= 11.4) {
            rank = "G";
        }
        return rank;
    }

    public static String jumpBaseRank(double jumpHeight) {
        var jump = calcJumpHeight(jumpHeight);
        var rank = "G";

        if (jump >= 5.0) {
            rank = "LEGEND";
        } else if (jump >= 4.75) {
            rank = "S++";
        } else if (jump >= 4.5) {
            rank = "S+";
        } else if (jump >= 4.25) {
            rank = "S";
        } else if (jump >= 4.0) {
            rank = "A++";
        } else if (jump >= 3.75) {
            rank = "A+";
        } else if (jump >= 3.5) {
            rank = "A";
        } else if (jump >= 3.25) {
            rank = "B++";
        } else if (jump >= 3.0) {
            rank = "B+";
        } else if (jump >= 2.75) {
            rank = "B";
        } else if (jump >= 2.5) {
            rank = "C++";
        } else if (jump >= 2.25) {
            rank = "C+";
        } else if (jump >= 2.0) {
            rank = "C";
        } else if (jump >= 1.75) {
            rank = "D";
        } else if (jump >= 1.5) {
            rank = "E";
        } else if (jump >= 1.25) {
            rank = "F";
        } else if (jump >= 1.0) {
            rank = "G";
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
