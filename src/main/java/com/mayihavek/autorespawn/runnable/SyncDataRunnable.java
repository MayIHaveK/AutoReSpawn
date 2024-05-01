package com.mayihavek.autorespawn.runnable;

import com.mayihavek.autorespawn.network.MessagePositionHistory;
import com.mayihavek.autorespawn.network.NetworkLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author MayIHaveK
 * @Date 2023/9/25 13:41
 */
public class SyncDataRunnable implements Runnable {
    @Override
    public void run() {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
        if(player == null || currentScreen == null){
            return;
        }
        if(currentScreen instanceof GuiGameOver){
            //客户端接收到信息，进行重生
            player.respawnPlayer();
            //开启线程
            final Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //判断玩家复活之后，发送给服务端位置
                    if(! Minecraft.getMinecraft().thePlayer.isDead){
                        //重生完毕之后发送给服务端，让服务端传送位置
                        MessagePositionHistory history = new MessagePositionHistory();
                        history.playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                        NetworkLoader.instance.sendToServer(history);
                        System.out.println("SyncDataRunnable: send position history to server");
                        timer.cancel();
                    }
                }
            };
            timer.schedule(timerTask,100,100);
        }


    }
}
