package com.mayihavek.autorespawn;

import com.mayihavek.autorespawn.config.ConfigLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main
{
    public static final String MODID = "autorespawn";
    public static final String VERSION = "1.0";
    public static final String NAME = "AutoRespawn";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigLoader.load(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
