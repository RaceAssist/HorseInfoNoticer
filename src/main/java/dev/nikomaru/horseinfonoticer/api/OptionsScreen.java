//package dev.nikomaru.horseinfonoticer.api;
//
//import net.fabricmc.fabric.impl.client.screen.ButtonList;
//import net.kyori.adventure.text.minimessage.MiniMessage;
//import net.minecraft.client.gui.screens.Screen;
//import net.minecraft.client.gui.widget.ButtonListWidget;
//import net.minecraft.network.chat.Component;
//
//public class OptionsScreen extends Screen {
//    private ButtonListWidget list;
//    private Screen screen;
//
//    public OptionsScreen(Screen screen) {
//        super(Component.literal("HorseInfoNoticer Config") );
//        this.screen = screen;
//    }
//
//    @Override
//    public void init() {
//        this.list = new ButtonList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
//    }
//}
