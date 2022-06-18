package dev.nikomaru.horseinfonoticer;

import dev.nikomaru.horseinfonoticer.renderer.HorseRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.world.entity.EntityType;

public class HorseInfoNoticer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityType.HORSE, HorseRenderer::new);
    }

}
