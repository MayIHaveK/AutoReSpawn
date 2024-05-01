package com.mayihavek.autorespawn.event;

import com.mayihavek.autorespawn.config.ConfigLoader;
import com.mayihavek.autorespawn.data.DeathData;
import com.mayihavek.autorespawn.network.MessagePositionHistory;
import com.mayihavek.autorespawn.network.NetworkLoader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MayIHaveK
 * @Date 2024/5/2 0:35
 */
public class AutoSpawnHandler {

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

    @SubscribeEvent
    public void onPlayerReSpawn(PlayerEvent.PlayerRespawnEvent event){
        //if(ConfigLoader.type != 1){
        //    return;
        //}
        //EntityPlayer player = event.player;
        //String name = player.getCommandSenderName();
        //ConcurrentHashMap<String, DeathData> hashMap = DeathData.deathDataHashMap;
        //if(!hashMap.containsKey(name)){
        //    return;
        //}
        ////通知给客户端，让客户端重生
        //MessagePositionHistory history = new MessagePositionHistory();
        //history.playerName = name;
        //NetworkLoader.instance.sendTo(history,(EntityPlayerMP)player);
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event){
        if(ConfigLoader.type != 1){
            return;
        }
        if (!(event.entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.entity;
        //记录死亡位置
        double posX = player.posX;
        double posY = player.posY;
        double posZ = player.posZ;
        int dimensionId = player.worldObj.provider.dimensionId;
        String name = player.getCommandSenderName();
        DeathData.deathDataHashMap.put(name, new DeathData(name,posX, posY, posZ, dimensionId));

        //通知给客户端，让客户端重生
        MessagePositionHistory history = new MessagePositionHistory();
        history.playerName = name;
        NetworkLoader.instance.sendTo(history,(EntityPlayerMP)player);

    }

    public static WorldServer getWorldByDimensionId(int dimensionId) {
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer[] worldServers = server.worldServers;

        for (WorldServer world : worldServers) {
            if (world.provider.dimensionId == dimensionId) {
                return world;
            }
        }

        return null;
    }

}
