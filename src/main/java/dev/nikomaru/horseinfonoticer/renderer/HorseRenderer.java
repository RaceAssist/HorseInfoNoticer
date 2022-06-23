package dev.nikomaru.horseinfonoticer.renderer;

import static dev.nikomaru.horseinfonoticer.utils.EntityUtil.getDisplayNameWithRank;
import static dev.nikomaru.horseinfonoticer.utils.EntityUtil.getHorseStatsString;

import com.google.common.collect.Maps;
import dev.nikomaru.horseinfonoticer.HorseInfoNoticer;
import dev.nikomaru.horseinfonoticer.utils.EntityUtil;
import dev.nikomaru.horseinfonoticer.utils.RenderUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HorseMarkingFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class HorseRenderer extends AbstractHorseEntityRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {


    private static final Map<HorseColor, Identifier> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(HorseColor.class), map -> {
        map.put(HorseColor.WHITE, new Identifier("textures/entity/horse/horse_white.png"));
        map.put(HorseColor.CREAMY, new Identifier("textures/entity/horse/horse_creamy.png"));
        map.put(HorseColor.CHESTNUT, new Identifier("textures/entity/horse/horse_chestnut.png"));
        map.put(HorseColor.BROWN, new Identifier("textures/entity/horse/horse_brown.png"));
        map.put(HorseColor.BLACK, new Identifier("textures/entity/horse/horse_black.png"));
        map.put(HorseColor.GRAY, new Identifier("textures/entity/horse/horse_gray.png"));
        map.put(HorseColor.DARKBROWN, new Identifier("textures/entity/horse/horse_darkbrown.png"));
    });

    public HorseRenderer(EntityRendererFactory.Context context) {
        super(context, new HorseEntityModel<>(context.getPart(EntityModelLayers.HORSE)), 1.1f);
        this.addFeature(new HorseMarkingFeatureRenderer(this));
        this.addFeature(new HorseArmorFeatureRenderer(this, context.getModelLoader()));
    }

    @Override public Identifier getTexture(HorseEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getColor());
    }


    public void render(@NotNull HorseEntity entity, float yaw, float partialTicks, @NotNull MatrixStack matrixStackIn,
            @NotNull VertexConsumerProvider bufferIn,
            int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoNoticer.isEnable()) {
            return;
        }

        var infoString = new ArrayList<String>();
        infoString.add(getDisplayNameWithRank(entity));

        var statsString = getHorseStatsString(entity);

        if (statsString != null) {
            infoString.addAll(statsString);
        }
        var stringAgeOrOwner = EntityUtil.getAgeOrOwnerString(entity);
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
