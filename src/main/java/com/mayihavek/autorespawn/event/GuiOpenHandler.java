package com.mayihavek.autorespawn.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author MayIHaveK
 * @Date 2024/5/2 0:35
 */
public class GuiOpenHandler {

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onGuiOpen(GuiOpenEvent event) {
        GuiScreen gui = event.gui;
        if (gui instanceof GuiGameOver) {
            Minecraft.getMinecraft().thePlayer.respawnPlayer();
        }
    }
}
