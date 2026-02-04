package com.tortugolen.echo_detectors.Compat.Ostrea;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class Ostrea {
    public static final String MOD_ID = "ostrea";

    public static boolean isLoaded() {
        return ModList.get().isLoaded(MOD_ID);
    }

    public static void init() {
        if (!ModList.get().isLoaded(MOD_ID)) {
            return;
        }
    }

    public static void register() {
        if (Ostrea.isLoaded()) {
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

            OstreaInitItems.ITEMS.register(modEventBus);
        }
    }
}
