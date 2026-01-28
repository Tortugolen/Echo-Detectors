package com.tortugolen.echo_detectors;

import com.tortugolen.echo_detectors.Init.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EchoDetectors.MOD_ID)
public class EchoDetectors {
    public static final String MOD_ID = "echo_detectors";
    public EchoDetectors() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitTabs.TABS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitEnchantments.ENCHANTMENTS.register(modEventBus);
        InitSounds.SOUND_EVENTS.register(modEventBus);
        InitMenus.MENUS.register(modEventBus);
        InitRecipes.SERIALIZERS.register(modEventBus);
        InitLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        InitTriggers.register();
    }
}
