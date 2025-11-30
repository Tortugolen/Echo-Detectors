package com.tortugolen.ostrea.Init;

import com.tortugolen.ostrea.Triggers.EffectRemovedTrigger;
import com.tortugolen.ostrea.Triggers.EnchantedItemTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class InitTriggers {
    public static final EnchantedItemTrigger ENCHANTED_ITEM = new EnchantedItemTrigger();
    public static final EffectRemovedTrigger EFFECT_REMOVED = new EffectRemovedTrigger();

    public static void register() {
        CriteriaTriggers.register(ENCHANTED_ITEM);
        CriteriaTriggers.register(EFFECT_REMOVED);
    }
}
