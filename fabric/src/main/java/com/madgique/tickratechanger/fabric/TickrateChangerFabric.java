package com.madgique.tickratechanger.fabric;

import com.madgique.tickratechanger.config.TickrateChangerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import com.madgique.tickratechanger.TickrateChanger;

public final class TickrateChangerFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        AutoConfig.register(TickrateChangerConfig.class, GsonConfigSerializer::new);

        new TickrateChanger().init();
    }
}
