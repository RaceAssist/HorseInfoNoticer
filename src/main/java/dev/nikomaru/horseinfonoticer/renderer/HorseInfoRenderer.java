package dev.nikomaru.horseinfonoticer.renderer;

import dev.nikomaru.horseinfonoticer.HorseInfoNoticer;
import dev.nikomaru.horseinfonoticer.utils.EntityUtil;
import dev.nikomaru.horseinfonoticer.utils.HorseEntityUtil;
import dev.nikomaru.horseinfonoticer.utils.HorseInfoStats;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.util.math.Box;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class HorseInfoRenderer {

    public static void renderHorseInfo(MatrixStack matrixStack, VertexConsumerProvider.Immediate vertexConsumers,
                                        Camera camera, RenderTickCounter tickCounter) {
        if (!HorseInfoNoticer.isEnable()) {
            return;
        }

        var mc = MinecraftClient.getInstance();
        if (mc.world == null || mc.player == null) {
            return;
        }

        var cameraPos = camera.getPos();
        var searchBox = new Box(
                cameraPos.x - 64, cameraPos.y - 64, cameraPos.z - 64,
                cameraPos.x + 64, cameraPos.y + 64, cameraPos.z + 64
        );

        var horses = mc.world.getEntitiesByClass(HorseEntity.class, searchBox, entity -> true);

        for (var horse : horses) {
            renderSingleHorseInfo(horse, matrixStack, vertexConsumers, camera, tickCounter);
        }
    }

    private static void renderSingleHorseInfo(HorseEntity entity, MatrixStack matrixStack,
                                               VertexConsumerProvider vertexConsumers, Camera camera,
                                               RenderTickCounter tickCounter) {
        var mc = MinecraftClient.getInstance();

        if (mc.player == EntityUtil.getRider(entity)) {
            return;
        }

        var cameraPos = camera.getPos();
        var d0 = entity.squaredDistanceTo(cameraPos);
        if (d0 >= 4096.0D) { // 64^2
            return;
        }

        var infoString = new ArrayList<String>();
        infoString.add(EntityUtil.getDisplayNameWithRank(entity));

        var statsString = EntityUtil.getHorseStatsString(entity);
        if (statsString != null) {
            infoString.addAll(statsString);
        }

        var stringAgeOrOwner = EntityUtil.getAgeOrOwnerString(entity);
        infoString.add(stringAgeOrOwner);

        renderEntityInfo(entity, infoString, matrixStack, vertexConsumers, camera, tickCounter);
    }

    private static void renderEntityInfo(HorseEntity entity, List<String> infoString, MatrixStack matrixStack,
                                          VertexConsumerProvider vertexConsumers, Camera camera,
                                          RenderTickCounter tickCounter) {
        var mc = MinecraftClient.getInstance();
        var cameraPos = camera.getPos();

        var scale = 0.025f;

        var rank = HorseEntityUtil.getEvaluateRankString(entity);
        var baseColor = HorseInfoStats.calcEvaluateRankColor(rank);

        var titleColor = baseColor;
        if (baseColor == Color.BLACK) {
            titleColor = Color.WHITE;
        }
        var fontColor = Color.WHITE;
        var f = entity.getHeight() + 0.5f;

        matrixStack.push();

        // エンティティの位置にトランスレート（カメラ位置からの相対位置）
        var tickDelta = tickCounter.getTickProgress(true);
        var entityPos = entity.getLerpedPos(tickDelta);
        matrixStack.translate(
                entityPos.x - cameraPos.x,
                entityPos.y - cameraPos.y + f,
                entityPos.z - cameraPos.z
        );

        matrixStack.multiply(camera.getRotation());
        matrixStack.scale(-scale, -scale, scale);

        var fontHeight = 10;
        float baseY = (4 - infoString.size()) * fontHeight - ((EntityUtil.getRider(entity) != null) ? fontHeight * 3 : fontHeight);

        var width = mc.textRenderer.getWidth(entity.getDisplayName().getString());
        for (var s : infoString) {
            width = Math.max(mc.textRenderer.getWidth(s), width);
        }
        var widthHalf = width / 2;

        var matrix4f = matrixStack.peek().getPositionMatrix();
        var f1 = mc.options.getTextBackgroundOpacity(0.4f);
        var r = (baseColor.getRed() / 255.0F) / 2.0F;
        var g = (baseColor.getGreen() / 255.0F) / 2.0F;
        var b = (baseColor.getBlue() / 255.0F) / 2.0F;
        var j = ((int) (f1 * 255.0F) << 24) + ((int) (r * 255.0F) << 16) + ((int) (g * 255.0F) << 8) + ((int) (b * 255.0F));

        for (var i = 0; i < infoString.size(); i++) {
            mc.textRenderer.draw(infoString.get(i), -widthHalf, (int) baseY + fontHeight * i,
                    (i == 0) ? titleColor.getRGB() : fontColor.getRGB(),
                    false, matrix4f, vertexConsumers, TextRenderer.TextLayerType.NORMAL, j, 0xF000F0);
        }

        matrixStack.pop();
    }
}
