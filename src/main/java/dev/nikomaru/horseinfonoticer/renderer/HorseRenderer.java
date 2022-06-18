package dev.nikomaru.horseinfonoticer.renderer;

import static dev.nikomaru.horseinfonoticer.utils.EntityUtil.getAgeOrOwnerString;
import static dev.nikomaru.horseinfonoticer.utils.EntityUtil.getDisplayNameWithRank;
import static dev.nikomaru.horseinfonoticer.utils.EntityUtil.getHorseStatsString;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.nikomaru.horseinfonoticer.utils.RenderUtil;
import net.minecraft.Util;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Variant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class HorseRenderer extends AbstractHorseRenderer<Horse, HorseModel<Horse>> {

    private static final Map<Variant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(Variant.class), map -> {
        map.put(Variant.WHITE, new ResourceLocation("textures/entity/horse/horse_white.png"));
        map.put(Variant.CREAMY, new ResourceLocation("textures/entity/horse/horse_creamy.png"));
        map.put(Variant.CHESTNUT, new ResourceLocation("textures/entity/horse/horse_chestnut.png"));
        map.put(Variant.BROWN, new ResourceLocation("textures/entity/horse/horse_brown.png"));
        map.put(Variant.BLACK, new ResourceLocation("textures/entity/horse/horse_black.png"));
        map.put(Variant.GRAY, new ResourceLocation("textures/entity/horse/horse_gray.png"));
        map.put(Variant.DARKBROWN, new ResourceLocation("textures/entity/horse/horse_darkbrown.png"));
    });

    public HorseRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel<>(context.bakeLayer(ModelLayers.HORSE)), 1.1F);
        addLayer(new HorseMarkingLayer(this));
        addLayer(new HorseArmorLayer(this, context.getModelSet()));
    }

    public ResourceLocation getTextureLocation(Horse entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }


    @Override
    public void render(@NotNull Horse entity, float yaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn,
            int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        var infoString = new ArrayList<String>();
        infoString.add(getDisplayNameWithRank(entity));

        var statsString = getHorseStatsString(entity);

        if (statsString != null) {
            infoString.addAll(statsString);
        }
        var stringAgeOrOwner = getAgeOrOwnerString(entity);
        if (stringAgeOrOwner != null) {
            infoString.add(stringAgeOrOwner);
        }

        RenderUtil.renderEntityInfo(
                entity,
                infoString,
                matrixStackIn,
                bufferIn,
                packedLightIn
        );
    }


}
