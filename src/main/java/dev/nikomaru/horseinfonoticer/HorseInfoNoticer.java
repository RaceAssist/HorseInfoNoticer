package dev.nikomaru.horseinfonoticer;

import dev.nikomaru.horseinfonoticer.renderer.HorseRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.EntityType;
import org.lwjgl.glfw.GLFW;

public class HorseInfoNoticer implements ClientModInitializer {

    static boolean enable = true;

    @Override
    public void onInitializeClient() {

        var KEYBINDING_MODE = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("horseinfonoticer.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinfonoticer.keybinding.category")
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KEYBINDING_MODE.consumeClick()) {
                toggle();
            }
        });

        EntityRendererRegistry.register(EntityType.HORSE, HorseRenderer::new);
    }

    private void toggle() {
        enable = !enable;

        var client = FabricClientAudiences.of().audience();
        var mm = MiniMessage.miniMessage();
        var message = "";
        if (enable) {
            message = I18n.get("horseinfonoticer.message.enable");
        } else {
            message = I18n.get("horseinfonoticer.message.disable");
        }
        client.sendActionBar(mm.deserialize(message));

    }

    public static boolean isEnable() {
        return enable;
    }

}
