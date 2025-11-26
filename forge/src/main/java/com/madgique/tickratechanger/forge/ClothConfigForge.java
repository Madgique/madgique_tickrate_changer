package com.madgique.tickratechanger.forge;

import com.madgique.tickratechanger.config.ClothConfigGUIBuilder;
import me.shedaniel.clothconfig2.ClothConfigDemo;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class ClothConfigForge {
    public static void registerConfig() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> {
            return ClothConfigGUIBuilder.getConfigBuilder().setParentScreen(parent).build();
        }));
    }
}