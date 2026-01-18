package dev.nikomaru.horseinfonoticer.utils;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.HorseEntity;

import java.util.List;

public class HorseEntityUtil {

    public static double getSpeed(HorseEntity entity) {
        return entity.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
    }

    public static double getJumpStrength(HorseEntity entity) {
        return entity.getAttributeValue(EntityAttributes.JUMP_STRENGTH);
    }


    public static String getEvaluateRankString(HorseEntity entity) {
        return HorseInfoStats.calcEvaluateRankString(getSpeed(entity), getJumpStrength(entity));
    }

    public static List<String> getStatsStrings(HorseEntity entity) {
        return HorseInfoFormat.formatHorseStats(
                entity.getHealth(),
                entity.getMaxHealth(),
                getSpeed(entity),
                getJumpStrength(entity));
    }

}
