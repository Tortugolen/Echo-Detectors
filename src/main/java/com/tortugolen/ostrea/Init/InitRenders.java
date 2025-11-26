package com.tortugolen.ostrea.Init;

import com.tortugolen.ostrea.GUIs.Screens.CrusherScreen;
import com.tortugolen.ostrea.GUIs.Screens.MechanicalOysterScreen;
import com.tortugolen.ostrea.GUIs.Screens.OysterScreen;
import com.tortugolen.ostrea.Models.PearlTips.CopperPearlTipProjectileModel;
import com.tortugolen.ostrea.Models.PearlTips.GoldPearlTipProjectileModel;
import com.tortugolen.ostrea.Models.PearlTips.IronPearlTipProjectileModel;
import com.tortugolen.ostrea.Models.PearlTips.PearlTipProjectileModel;
import com.tortugolen.ostrea.Renderers.BlockRenderers;
import com.tortugolen.ostrea.Renderers.PearlTips.CopperPearlTipProjectileRenderer;
import com.tortugolen.ostrea.Renderers.PearlTips.GoldPearlTipProjectileRenderer;
import com.tortugolen.ostrea.Renderers.PearlTips.IronPearlTipProjectileRenderer;
import com.tortugolen.ostrea.Renderers.PearlTips.PearlTipProjectileRenderer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "ostrea", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class InitRenders {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(InitEntities.PEARL_TIP.get(), PearlTipProjectileRenderer::new);
        event.registerEntityRenderer(InitEntities.IRON_PEARL_TIP.get(), IronPearlTipProjectileRenderer::new);
        event.registerEntityRenderer(InitEntities.COPPER_PEARL_TIP.get(), CopperPearlTipProjectileRenderer::new);
        event.registerEntityRenderer(InitEntities.GOLD_PEARL_TIP.get(), GoldPearlTipProjectileRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(BlockRenderers::registerBlockRenderLayers);
        MenuScreens.register(InitMenus.OYSTER_MENU.get(), OysterScreen::new);
        MenuScreens.register(InitMenus.MECHANICAL_OYSTER_MENU.get(), MechanicalOysterScreen::new);
        MenuScreens.register(InitMenus.CRUSHER_MENU.get(), CrusherScreen::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PearlTipProjectileModel.LAYER_LOCATION, PearlTipProjectileModel::createBodyLayer);
        event.registerLayerDefinition(IronPearlTipProjectileModel.LAYER_LOCATION, IronPearlTipProjectileModel::createBodyLayer);
        event.registerLayerDefinition(CopperPearlTipProjectileModel.LAYER_LOCATION, CopperPearlTipProjectileModel::createBodyLayer);
        event.registerLayerDefinition(GoldPearlTipProjectileModel.LAYER_LOCATION, GoldPearlTipProjectileModel::createBodyLayer);
    }
}
