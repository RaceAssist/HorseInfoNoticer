package dev.nikomaru.horseinfonoticer.mixin;

import dev.nikomaru.horseinfonoticer.renderer.HorseInfoRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(
            method = "renderEntities",
            at = @At("TAIL")
    )
    private void afterRenderEntities(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers,
                                      Camera camera, RenderTickCounter tickCounter, List<Entity> entities,
                                      CallbackInfo ci) {
        HorseInfoRenderer.renderHorseInfo(matrices, vertexConsumers, camera, tickCounter);
    }
}
