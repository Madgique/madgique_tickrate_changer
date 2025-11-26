package com.madgique.tickratechanger.handlers;

import com.madgique.tickratechanger.TickrateChanger;
import com.madgique.tickratechanger.api.TickrateAPI;
import dev.architectury.event.events.common.PlayerEvent;

public enum TickrateChangerOnClientConnect {
    INSTANCE;

    public void init() {
        PlayerEvent.PLAYER_JOIN.register(player -> {
            TickrateAPI.changeClientTickrate(player, TickrateChanger.CONFIG.defaultTickrate);
        });

    }
}
