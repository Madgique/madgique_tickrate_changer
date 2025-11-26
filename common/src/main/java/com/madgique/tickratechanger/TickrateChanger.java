package com.madgique.tickratechanger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.madgique.tickratechanger.api.TickrateAPI;
import com.madgique.tickratechanger.config.TickrateChangerConfig;
import com.madgique.tickratechanger.handlers.TickrateChangerEventHandler;
import com.madgique.tickratechanger.handlers.TickrateChangerOnClientConnect;
import com.madgique.tickratechanger.handlers.TickrateChangerOnServerStartedHandler;
import com.madgique.tickratechanger.mixin.MinecraftAccessor;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Timer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

public final class TickrateChanger {
    public static final String MOD_ID = "madgique_tickrate_changer";

    public static TickrateChanger INSTANCE;
    public static TickrateChangerConfig CONFIG;

    public static Logger LOGGER = LogManager.getLogger("Tickrate Changer");
    public static final ResourceLocation TICKRATE = new ResourceLocation(MOD_ID, "tickrate");

    // Gamerule
    public static final String GAME_RULE = "tickrate";

    // Default tickrate - can be changed in the config file
    public static float DEFAULT_VALUE_DEFAULT_TICKRATE = 20.0F;
    // Stored client-side tickrate
    public static float TICKS_PER_SECOND = 20.0F;
    // Server-side tickrate in miliseconds
    public static long MILISECONDS_PER_TICK = 50L;
    // Sound speed
    public static float GAME_SPEED = 1.0F;
    // Min Tickrate
    public static float DEFAULT_VALUE_MIN_TICKRATE = 0.1F;
    // Max Tickrate
    public static float DEFAULT_VALUE_MAX_TICKRATE = 1000.0F;
    // Show Messages
    public static boolean DEFAULT_VALUE_SHOW_MESSAGES = true;
    // Change sound speed
    public static boolean DEFAULT_VALUE_CHANGE_SOUND = true;

    public TickrateChanger() {
        INSTANCE = this;
        CONFIG = AutoConfig.getConfigHolder(TickrateChangerConfig.class).getConfig();
    }

    public void init() {
        TickrateChangerEventHandler.INSTANCE.init();
        TickrateChangerOnServerStartedHandler.INSTANCE.init();
        TickrateChangerOnClientConnect.INSTANCE.init();
    }

    public static void applyDefaultTickrate(MinecraftServer server) {
        float defaultTickrate = TickrateChanger.CONFIG.defaultTickrate;
        TickrateAPI.changeTickrate(defaultTickrate, server); // Applique le tickrate et affiche un message si nécessaire
        TickrateChanger.LOGGER.info("Default tickrate of " + defaultTickrate + " applied on server start.");
    }

    @Environment(EnvType.CLIENT)
    public void updateClientTickrate(float tickrate, boolean log) {
        if (log)
            LOGGER.info("Updating client tickrate to " + tickrate);

        TICKS_PER_SECOND = tickrate;
        if (DEFAULT_VALUE_CHANGE_SOUND)
            GAME_SPEED = tickrate / 20F;

        Minecraft mc = Minecraft.getInstance();
        if (mc == null)
            return; // Wut

        Timer timer = ((MinecraftAccessor) mc).getTimer();

        // change tickrate client-side
        ((ITickrateChanger) (Object) timer).changeClientTickrate(tickrate, System.currentTimeMillis());
    }

    public void updateServerTickrate(float tickrate, boolean log) {
        if (log)
            LOGGER.info("Updating server tickrate to " + tickrate);

        // change tickrate server-side
        MILISECONDS_PER_TICK = (long) (1000L / tickrate);
    }
}
