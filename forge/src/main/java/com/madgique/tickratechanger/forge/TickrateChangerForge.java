package com.madgique.tickratechanger.forge;

import com.madgique.tickratechanger.config.TickrateChangerConfig;
import com.madgique.tickratechanger.network.TickrateChangerNetwork;
import dev.architectury.platform.forge.EventBuses;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;


import com.madgique.tickratechanger.TickrateChanger;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TickrateChanger.MOD_ID)
public final class TickrateChangerForge {
    public TickrateChangerForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(TickrateChanger.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        //Run our common setup.
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TickrateChangerNetwork::registerPackets);
        AutoConfig.register(TickrateChangerConfig.class, Toml4jConfigSerializer::new);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClothConfigForge::registerConfig);
        new TickrateChanger().init();
    }
}