package dev.nikomaru.horseinfonoticer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class HorseInfoNoticer implements ClientModInitializer {

    public static boolean enable = true;
    static int mode = 0;

    public static int getMode() {
        return mode;
    }

    public static boolean isEnable() {
        return enable;
    }

    @Override
    public void onInitializeClient() {

        var KEYBINDING_ENABLE = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("horseinfonoticer.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinfonoticer.keybinding.category")
        );

        var KEYBINDING_MODE = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("horseinfonoticer.keybinding.desc.mode", GLFW.GLFW_KEY_J, "horseinfonoticer.keybinding.category")
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KEYBINDING_ENABLE.wasPressed()) {
                toggleEnable();
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KEYBINDING_MODE.wasPressed()) {
                toggleMode();
            }
        });

    }

    private void toggleMode() {
        mode++;
        var message = Text.translatable("horseinfonoticer.message.mode." + mode).getString();

        if (mode >= 2) {
            mode = -1;
        }

        var client = MinecraftClientAudiences.of().audience();
        var mm = MiniMessage.miniMessage();

        client.sendActionBar(mm.deserialize(message));
    }

    private void toggleEnable() {
        enable = !enable;

        var client = MinecraftClientAudiences.of().audience();
        var mm = MiniMessage.miniMessage();
        var message = "";
        if (enable) {
            message = Text.translatable("horseinfonoticer.message.enable").getString();
        } else {
            message = Text.translatable("horseinfonoticer.message.disable").getString();
        }
        client.sendActionBar(mm.deserialize(message));
    }

}
