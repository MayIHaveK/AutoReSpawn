package com.mayihavek.autorespawn.event;

import com.mayihavek.autorespawn.config.ConfigLoader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author MayIHaveK
 * @Date 2024/5/2 3:14
 */
public class GuiOpenHandler {

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        GuiScreen gui = event.gui;
        if (gui instanceof GuiGameOver) {
            EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
            int type = ConfigLoader.type;
            if (type == 0) {
                player.respawnPlayer();
            }
        }
    }

}
