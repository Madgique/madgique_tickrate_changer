package com.madgique.tickratechanger.mixin;

import net.minecraft.client.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Timer.class)
public interface TimerAccessor {
    @Accessor("msPerTick")
    void setMsPerTick(float msPerTick);

    @Accessor("lastMs")
    void setLastMs(long lastMs);
}
