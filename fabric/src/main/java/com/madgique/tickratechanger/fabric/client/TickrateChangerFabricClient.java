package com.madgique.tickratechanger.fabric.client;

import com.madgique.tickratechanger.network.TickrateChangerNetwork;
import net.fabricmc.api.ClientModInitializer;

public final class TickrateChangerFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TickrateChangerNetwork.registerPackets();
    }
}
