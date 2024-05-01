package com.mayihavek.autorespawn.network;

import com.mayihavek.autorespawn.Main;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Mayihavek
 */
public class NetworkLoader {

    public static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);

    public static void init() {
        instance.registerMessage(MessagePositionHistory.Handler.class, MessagePositionHistory.class, 0,Side.SERVER);
        instance.registerMessage(MessagePositionHistory.Handler.class, MessagePositionHistory.class, 1,Side.CLIENT);

    }



}
