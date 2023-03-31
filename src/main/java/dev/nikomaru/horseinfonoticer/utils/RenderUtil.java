package dev.nikomaru.horseinfonoticer.utils;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

public class RenderUtil {

    public static void renderEntityInfo(HorseEntity entity, List<String> infoString, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn,
                                        int packedLightIn) {
        var mc = MinecraftClient.getInstance();
        if (mc.player == EntityUtil.getRider(entity)) {
            return;
        }
        var d0 = entity.distanceTo(Objects.requireNonNull(mc.getCameraEntity()));
        if (d0 >= 2048.0D) {
            return;
        }
        var scale = 0.025f;

        //ランクの取得
        var rank = HorseEntityUtil.getEvaluateRankString(entity);
        //ランクから色を取得
        var baseColor = HorseInfoStats.calcEvaluateRankColor(rank);


        var titleColor = baseColor;
        if (baseColor == Color.BLACK) {
            titleColor = Color.WHITE;
        }
        var fontColor = Color.WHITE;
        var f = entity.getHeight() + 2.0f;
        matrixStackIn.push();
        matrixStackIn.translate(0.0, f, 0.0);
        matrixStackIn.multiply(mc.gameRenderer.getCamera().getRotation());
        matrixStackIn.scale(-scale, -scale, scale);
        var fontHeight = 10;
        float baseY = (4 - infoString.size()) * fontHeight - ((EntityUtil.getRider(entity) != null) ? fontHeight * 3 : fontHeight);

        var width = mc.textRenderer.getWidth(entity.getDisplayName().getString());
        for (var s : infoString) {
            width = Math.max(mc.textRenderer.getWidth(s), width);
        }
        var widthHalf = width / 2.0F;

        var matrix4f = matrixStackIn.peek().getPositionMatrix();
        var f1 = mc.options.getTextBackgroundOpacity(0.4f);
        var r = (baseColor.getRed() / 255.0F) / 2.0F;
        var g = (baseColor.getGreen() / 255.0F) / 2.0F;
        var b = (baseColor.getBlue() / 255.0F) / 2.0F;
        var j = ((int) (f1 * 255.0F) << 24) + ((int) (r * 255.0F) << 16) + ((int) (g * 255.0F) << 8) + ((int) (b * 255.0F));

        for (var i = 0; i < infoString.size(); i++) {
            mc.textRenderer.draw(infoString.get(i), -widthHalf, baseY + fontHeight * i, (i == 0) ? titleColor.getRGB() :
                            fontColor.getRGB(),
                    false, matrix4f, bufferIn, TextRenderer.TextLayerType.NORMAL, j, packedLightIn);
        }
        matrixStackIn.pop();
    }


}
