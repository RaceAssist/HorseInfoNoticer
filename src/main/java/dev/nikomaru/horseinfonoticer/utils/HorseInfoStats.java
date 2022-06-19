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
        if(HorseInfoNoticer.getMode() == 1) {
            return speedBaseRank(paramSpeed);
        }else if(HorseInfoNoticer.getMode() == -1) {
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
        if (speed >= 14.2) {
            rank = "LEGEND";
        } else if (speed >= 14.15) {
            rank = "S++";
        } else if (speed >= 14.10) {
            rank = "S+";
        } else if (speed >= 13.95) {
            rank = "S";
        } else if (speed >= 13.90) {
            rank = "A++";
        } else if (speed >= 13.80) {
            rank = "A+";
        } else if (speed >= 13.70) {
            rank = "A";
        } else if (speed >= 13.55) {
            rank = "B++";
        } else if (speed >= 13.30) {
            rank = "B+";
        } else if (speed >= 13.0) {
            rank = "B";
        } else if (speed >= 12.5) {
            rank = "C++";
        } else if (speed >= 12.0) {
            rank = "C+";
        } else if (speed >= 11.5) {
            rank = "C";
        } else if (speed >= 10.5) {
            rank = "D";
        } else if (speed >= 9.5) {
            rank = "E";
        } else if (speed >= 8.5) {
            rank = "F";
        } else if (speed >= 7.5) {
            rank = "G";
        }
        return rank;
    }

    public static String jumpBaseRank(double jumpHeight) {
        var jump = calcJumpHeight(jumpHeight);
        var rank = "G";

        if(jump >= 5.125) {
            rank = "LEGEND";
        }else if(jump >= 4.95) {
            rank = "S++";
        }else if (jump >= 4.775){
            rank = "S+";
        }else if (jump >= 4.60) {
            rank = "S";
        }else if (jump >= 4.425) {
            rank = "A++";
        }else if (jump >= 4.25) {
            rank = "A+";
        }else if (jump >= 4.075) {
            rank = "A";
        }else if (jump >= 3.90) {
            rank = "B++";
        }else if (jump >= 3.725) {
            rank = "B+";
        }else if (jump >= 3.55) {
            rank = "B";
        }else if (jump >= 3.375) {
            rank = "C++";
        }else if (jump >= 3.20) {
            rank = "C+";
        }else if (jump >= 3.025) {
            rank = "C";
        }else if (jump >= 2.85) {
            rank = "D";
        }else if (jump >= 2.675) {
            rank = "E";
        }else if (jump >= 2.50) {
            rank = "F";
        }else if (jump >= 2.325) {
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
