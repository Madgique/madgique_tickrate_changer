package com.madgique.tickratechanger.handlers;

import com.madgique.tickratechanger.TickrateChanger;
import dev.architectury.event.events.common.LifecycleEvent;


public enum TickrateChangerOnServerStartedHandler {
    INSTANCE;

    public void init() {
        LifecycleEvent.SERVER_STARTED.register(server -> {
            // Applique le tickrate par défaut de la configuration
            TickrateChanger.applyDefaultTickrate(server);
        });

    }
}
