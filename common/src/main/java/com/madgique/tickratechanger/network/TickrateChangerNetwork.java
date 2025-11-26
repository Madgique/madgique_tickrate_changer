package com.madgique.tickratechanger.network;


import com.madgique.tickratechanger.TickrateChanger;
import dev.architectury.networking.NetworkManager;

public class TickrateChangerNetwork {

    public static void registerPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, TickrateChanger.TICKRATE, ((buf, context) -> {
            float tickrate = buf.readFloat();
            TickrateChanger.LOGGER.info("Tickrate received from Server: " + tickrate);

            if (tickrate < TickrateChanger.CONFIG.minTickrate) {
                TickrateChanger.LOGGER.info("Tickrate forced to change from " + tickrate + " to " +
                        TickrateChanger.CONFIG.minTickrate + ", because the value is too low" +
                        " (You can change the minimum tickrate in the config)");
                tickrate = TickrateChanger.CONFIG.minTickrate;
            } else if (tickrate > TickrateChanger.CONFIG.maxTickrate) {
                TickrateChanger.LOGGER.info("Tickrate forced to change from " + tickrate + " to " +
                        TickrateChanger.CONFIG.maxTickrate + ", because the value is too high" +
                        " (You can change the maximum tickrate in the config)");
                tickrate = TickrateChanger.CONFIG.maxTickrate;
            }
            TickrateChanger.INSTANCE.updateClientTickrate(tickrate, TickrateChanger.CONFIG.showMessages);
        }));

    }
}
