package com.tortugolen.echo_detectors.Events;

import com.tortugolen.echo_detectors.EchoDetectors;
import com.tortugolen.echo_detectors.Init.InitItems;
import com.tortugolen.echo_detectors.Items.AbstractDetectorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EchoDetectors.MOD_ID)
public class SmithingDetectorUpgradeHandler {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (!isDetector(left)) return;

        if (right.is(InitItems.LOCATION_UPGRADE.get())) {
            if (!AbstractDetectorItem.hasLocationUpgrade(left)) {
                ItemStack result = left.copy();
                AbstractDetectorItem.applyLocationUpgrade(result);
                event.setOutput(result);
                event.setCost(5);
                event.setMaterialCost(1);
            }
        }

        if (right.is(InitItems.NAME_UPGRADE.get())) {
            if (!AbstractDetectorItem.hasNameUpgrade(left)) {
                ItemStack result = left.copy();
                AbstractDetectorItem.applyNameUpgrade(result);
                event.setOutput(result);
                event.setCost(5);
                event.setMaterialCost(1);
            }
        }
    }

    private static boolean isDetector(ItemStack pStack) {
        return pStack.is(InitItems.METAL_DETECTOR.get()) || pStack.is(InitItems.ECHO_DETECTOR.get()) || pStack.is(InitItems.RESONANT_DETECTOR.get());
    }
}