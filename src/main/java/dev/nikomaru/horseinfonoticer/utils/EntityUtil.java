package dev.nikomaru.horseinfonoticer.utils;

import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

import java.util.List;
import java.util.UUID;

public class EntityUtil {

    public static String getDisplayNameString(Nameable entity) {
        return entity.getDisplayName().getString();
    }

    private static String getOwnerString(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        var ownerName = "Unknown";

        ownerName = HorseInfoCore.playerNameManager.getPlayerName(uuid);

        return "(Owner: " + ownerName + ")";
    }


    public static String getAgeOrOwnerString(TamableAnimal entity) {
        return entity.isBaby() ? "(Baby)" : getOwnerString(entity.getOwnerUUID());
    }

    public static String getAgeOrOwnerString(AbstractHorse entity) {
        return entity.isBaby() ? "(Baby)" : getOwnerString(entity.getOwnerUUID());
    }

    public static String getDisplayNameWithRank(AbstractHorse entity) {
        return HorseInfoFormat.formatHorseNameWithRank(
                EntityUtil.getDisplayNameString(entity),
                HorseEntityUtil.getEvaluateRankString(entity)
        );
    }

    public static List<String> getHorseStatsString(AbstractHorse entity) {
        var passengers = entity.getPassengers();

        if (passengers == null || passengers.size() == 0) {
            return HorseEntityUtil.getStatsStrings(entity);
        }
        return null;
    }

    public static Entity getRider(Entity entity) {
        var passengers = entity.getPassengers();
        if (passengers == null || passengers.size() == 0) {
            return null;
        }

        return passengers.get(0);
    }


}
