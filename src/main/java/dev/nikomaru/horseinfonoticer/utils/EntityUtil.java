package dev.nikomaru.horseinfonoticer.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.util.Nameable;
import java.util.List;
import java.util.UUID;

public class EntityUtil {

    public static String getDisplayNameString(Nameable entity) {
        return entity.getDisplayName().getString();
    }

    private static String getOwnerString(UUID uuid) {
        if (uuid == null) {
            return "(Owner: Not Found)";
        }

        var ownerName = "Unknown";

        ownerName = HorseInfoCore.playerNameManager.getPlayerName(uuid);

        return "(Owner: " + ownerName + ")";
    }


    public static String getAgeOrOwnerString(HorseEntity entity) {
        return entity.isBaby() ? "(Baby)" : getOwnerString(entity.getOwnerUuid());
    }

    public static String getDisplayNameWithRank(HorseEntity entity) {
        return HorseInfoFormat.formatHorseNameWithRank(
                EntityUtil.getDisplayNameString(entity),
                HorseEntityUtil.getEvaluateRankString(entity)
        );
    }

    public static List<String> getHorseStatsString(HorseEntity entity) {
        var passengers = entity.getPassengerList();

        if (passengers == null || passengers.size() == 0) {
            return HorseEntityUtil.getStatsStrings(entity);
        }
        return null;
    }

    public static Entity getRider(HorseEntity entity) {
        var passengers = entity.getPassengerList();
        if (passengers == null || passengers.size() == 0) {
            return null;
        }

        return passengers.get(0);
    }


}
