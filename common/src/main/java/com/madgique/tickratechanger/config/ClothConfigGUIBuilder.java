package com.madgique.tickratechanger.config;

import com.madgique.tickratechanger.TickrateChanger;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import me.shedaniel.autoconfig.AutoConfig;

public class ClothConfigGUIBuilder implements ConfigData {
    public ClothConfigGUIBuilder() {
    }

    public static ConfigBuilder getConfigBuilder() {
        ConfigBuilder builder = ConfigBuilder.create().setTitle(Component.literal("Madgique's Tickrate Changer Config")).setSavingRunnable(() -> {
            AutoConfig.getConfigHolder(TickrateChangerConfig.class).save();
        });
        builder.setDefaultBackgroundTexture(new ResourceLocation("minecraft:textures/block/oak_planks.png"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory testing = builder.getOrCreateCategory(Component.literal("category pour tester"));

        // Tickrate related
        testing.addEntry(entryBuilder.startFloatField(Component.literal("Default Tickrate"), TickrateChanger.CONFIG.defaultTickrate).setDefaultValue(TickrateChanger.DEFAULT_VALUE_DEFAULT_TICKRATE).setSaveConsumer(newValue -> TickrateChanger.CONFIG.defaultTickrate = newValue).build());
        testing.addEntry(entryBuilder.startFloatField(Component.literal("Min Tickrate"), TickrateChanger.CONFIG.minTickrate).setDefaultValue(TickrateChanger.DEFAULT_VALUE_MIN_TICKRATE).setSaveConsumer(newValue -> TickrateChanger.CONFIG.minTickrate = newValue).build());
        testing.addEntry(entryBuilder.startFloatField(Component.literal("Max Tickrate"), TickrateChanger.CONFIG.maxTickrate).setDefaultValue(TickrateChanger.DEFAULT_VALUE_MAX_TICKRATE).setSaveConsumer(newValue -> TickrateChanger.CONFIG.maxTickrate = newValue).build());

        // Booleans
        testing.addEntry(entryBuilder.startBooleanToggle(Component.literal("Change Sound"), TickrateChanger.CONFIG.changeSound).setDefaultValue(TickrateChanger.DEFAULT_VALUE_CHANGE_SOUND).setSaveConsumer(newValue -> TickrateChanger.CONFIG.changeSound = newValue).build());
        //testing.addEntry(entryBuilder.startBooleanToggle(new TextComponent("Keys Availables"), true).setDefaultValue(keyBindings).build());
        testing.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Messages"), TickrateChanger.CONFIG.showMessages).setDefaultValue(TickrateChanger.DEFAULT_VALUE_SHOW_MESSAGES).setSaveConsumer(newValue -> TickrateChanger.CONFIG.showMessages = newValue).build());

        return builder;
    }
}
