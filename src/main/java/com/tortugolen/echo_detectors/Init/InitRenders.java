package com.tortugolen.echo_detectors.Init;

import com.tortugolen.echo_detectors.EchoDetectors;
import com.tortugolen.echo_detectors.GUIs.Screens.EchoDetectorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EchoDetectors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class InitRenders {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(InitMenus.ECHO_DETECTOR_MENU.get(), EchoDetectorScreen::new);
    }
}
