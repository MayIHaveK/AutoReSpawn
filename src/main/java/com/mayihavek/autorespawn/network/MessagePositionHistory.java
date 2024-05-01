package com.mayihavek.autorespawn.network;

import com.mayihavek.autorespawn.config.ConfigLoader;
import com.mayihavek.autorespawn.data.DeathData;
import com.mayihavek.autorespawn.runnable.SyncDataRunnable;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Mayihavek
 */
public class MessagePositionHistory implements IMessage {

    public String playerName;


    @Override
    public void fromBytes(ByteBuf buf) {
        playerName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playerName);
    }

    public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static class Handler implements IMessageHandler<MessagePositionHistory, IMessage> {
        @Override
        public IMessage onMessage(MessagePositionHistory message, MessageContext ctx) {
            if (ctx.side == Side.SERVER && ConfigLoader.type == 1) {
                //服务端接收到信息，传送玩家位置
                String name = message.playerName;
                EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
                if (player != null) {
                    ConcurrentHashMap<String, DeathData> hashMap = DeathData.deathDataHashMap;
                    if (hashMap.containsKey(name)) {
                        DeathData data = hashMap.get(name);

                        if (player.dimension != data.dimensionId) {
                            WorldServer targetWorld = MinecraftServer.getServer().worldServerForDimension(data.dimensionId);
                            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, data.dimensionId, new CustomTeleporter(targetWorld, data.posX, data.posY, data.posZ));
                        } else {
                            player.setPositionAndUpdate(data.posX, data.posY, data.posZ);
                        }

                        //删除数据
                        DeathData.deathDataHashMap.remove(name);
                    }
                }
                executor.shutdown();
            }

            if (ctx.side == Side.CLIENT && ConfigLoader.type == 1) {
                //开启一个线程定时检测，线程池为 8
                executor = Executors.newScheduledThreadPool(1);
                Runnable task = new SyncDataRunnable();
                executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
            }

            return null;
        }
    }

    public static class CustomTeleporter extends Teleporter {
        private final WorldServer world;
        private double x, y, z;

        public CustomTeleporter(WorldServer world, double x, double y, double z) {
            super(world);
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public void placeInPortal(Entity entity,  double p_77185_2_, double p_77185_4_, double p_77185_6_, float r) {
            entity.setPosition(x, y, z);
            entity.motionX = 0.0;
            entity.motionY = 0.0;
            entity.motionZ = 0.0;
        }
    }


}
