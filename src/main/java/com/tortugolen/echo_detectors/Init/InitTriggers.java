package com.tortugolen.echo_detectors.Init;

import com.tortugolen.echo_detectors.Triggers.Abstract.AbstractTrigger0;
import net.minecraft.advancements.CriteriaTriggers;

public class InitTriggers {
    public static final AbstractTrigger0 ABSTRACT0 = new AbstractTrigger0();

    public static void register() {
        CriteriaTriggers.register(ABSTRACT0);
    }
}
