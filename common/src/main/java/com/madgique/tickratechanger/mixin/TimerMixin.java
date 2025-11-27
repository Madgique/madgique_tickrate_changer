package com.madgique.tickratechanger.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import com.madgique.tickratechanger.ITickrateChanger;

import net.minecraft.client.Timer;

@Mixin(Timer.class)
public class TimerMixin implements ITickrateChanger {
    @Shadow
    @Mutable
    private float msPerTick;

    public void changeClientTickrate(float f, long l) {
        TimerAccessor accessor = (TimerAccessor) (Object) this;
        accessor.setMsPerTick(1000.0F / f);
    }
}
