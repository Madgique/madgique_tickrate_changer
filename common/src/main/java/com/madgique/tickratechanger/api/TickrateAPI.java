package com.madgique.tickratechanger.api;

import com.madgique.tickratechanger.TickrateChanger;

import com.madgique.tickratechanger.config.TickrateChangerConfig;
import com.madgique.tickratechanger.network.TickrateMessage;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import me.shedaniel.autoconfig.AutoConfig;


public class TickrateAPI {
    /**
     * Let you change the client & server tickrate
     * Can only be called from server-side. Can also be called from client-side if is singleplayer.
     * @param ticksPerSecond Tickrate to be set
     */
    public static void changeTickrate(float ticksPerSecond, MinecraftServer server) {
        changeTickrate(ticksPerSecond, server, TickrateChanger.CONFIG.showMessages);
    }

    /**
     * Let you change the client & server tickrate
     * Can only be called from server-side. Can also be called from client-side if is singleplayer.
     * @param ticksPerSecond Tickrate to be set
     * @param log If should send console logs
     */
    public static void changeTickrate(float ticksPerSecond, MinecraftServer server, boolean log) {
        changeServerTickrate(ticksPerSecond, log);
        changeClientTickrate(ticksPerSecond, server, log);
    }

    /**
     * Let you change the server tickrate
     * Can only be called from server-side. Can also be called from client-side if
     * is singleplayer.
     *
     * @param ticksPerSecond Tickrate to be set
     */
    public static void changeServerTickrate(float ticksPerSecond) {
        changeServerTickrate(ticksPerSecond, TickrateChanger.CONFIG.showMessages);
    }

    /**
     * Let you change the server tickrate
     * Can only be called from server-side. Can also be called from client-side if
     * is singleplayer.
     *
     * @param ticksPerSecond Tickrate to be set
     * @param log            If should send console logs
     */
    public static void changeServerTickrate(float ticksPerSecond, boolean log) {
        TickrateChanger.INSTANCE.updateServerTickrate(ticksPerSecond, log);
    }

    /**
     * Let you change the all clients tickrate
     * Can be called either from server-side or client-side
     * @param ticksPerSecond Tickrate to be set
     */
    public static void changeClientTickrate(float ticksPerSecond, MinecraftServer server) {
        changeClientTickrate(ticksPerSecond, server, TickrateChanger.CONFIG.showMessages);
    }

    /**
     * Let you change the all clients tickrate
     * Can be called either from server-side or client-side
     * @param ticksPerSecond Tickrate to be set
     * @param log If should send console logs
     */
    public static void changeClientTickrate(float ticksPerSecond, MinecraftServer server, boolean log) {
        if(server != null && server.getPlayerList() != null) { // Is a server or singleplayer
            for(ServerPlayer p : server.getPlayerList().getPlayers()) {
                changeClientTickrate(p, ticksPerSecond, log);
            }
        } else { // Is in menu or a player connected in a server. We can say this is client.
            changeClientTickrate(null, ticksPerSecond, log);
        }

    }

    /**
     * Let you change the all clients tickrate
     * Can be called either from server-side or client-side.
     * Will only take effect in the client-side if the player is Minecraft.thePlayer
     * @param player The Player
     * @param ticksPerSecond Tickrate to be set
     */
    public static void changeClientTickrate(Player player, float ticksPerSecond) {
        changeClientTickrate(player, ticksPerSecond, TickrateChanger.CONFIG.showMessages);
    }

    /**
     * Let you change the all clients tickrate
     * Can be called either from server-side or client-side.
     * Will only take effect in the client-side if the player is Minecraft.thePlayer
     * @param player The Player
     * @param ticksPerSecond Tickrate to be set
     * @param log If should send console logs
     */
    public static void changeClientTickrate(Player player, float ticksPerSecond, boolean log) {
        if((player == null) || (player.level().isClientSide)) { // Client
            if(Platform.getEnvironment() != Env.CLIENT) return;
            if((player != null) && (player != Minecraft.getInstance().player)) return;
            TickrateChanger.INSTANCE.updateClientTickrate(ticksPerSecond, log);
        } else { // Server
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            TickrateMessage message = new TickrateMessage(ticksPerSecond);
            message.encode(buf);
            NetworkManager.sendToPlayer((ServerPlayer) player, TickrateChanger.TICKRATE, buf);
        }
    }

    /**
     * Let you change the default tickrate
     * Can only be called from server-side. Can also be called from client-side if
     * is singleplayer.
     * This will not update the tickrate from the server/clients.
     *
     * @param ticksPerSecond Tickrate to be set
     * @param save           If will be saved in the config file
     */
    public static void changeDefaultTickrate(float ticksPerSecond, boolean save) {
        TickrateChanger.CONFIG.defaultTickrate = ticksPerSecond;
        if (save)
            AutoConfig.getConfigHolder(TickrateChangerConfig.class).save();
    }

    /**
     * Only returns the real tickrate if you call the method server-side or in singleplayer
     * @return The server tickrate or the client server tickrate if it doesn't have access to the real tickrate.
     */
    public static float getServerTickrate() {
        return 1000F / TickrateChanger.MILISECONDS_PER_TICK;
    }

    /**
     * Can only be called in the client-side
     * @return The client tickrate
     */
    public static float getClientTickrate() {
        return TickrateChanger.TICKS_PER_SECOND;
    }
}
