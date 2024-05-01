package com.mayihavek.autorespawn.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

/**
 * @author MayIHaveK
 * @Date 2024/5/2 0:38
 */
public class ConfigLoader {

    private static Configuration config;

    private static Logger logger;

    public static int type;

    public static void load(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());

        //然后我们读入配置：
        config.load();
        load();
    }

    public static void load() {
        logger.info("开始加载配置！ ");
        String comment;
        comment = "重生类型：0为普通重生，1为原地重生（实际上是复活后传送回去）";
        type = config.get(Configuration.CATEGORY_GENERAL, "type", 0, comment).getInt();
        config.save();
        logger.info("配置加载完毕！ ");
    }

    public static Logger logger() {
        return logger;
    }
}
