package com.tortugolen.echo_detectors.Init;

import com.tortugolen.echo_detectors.EchoDetectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EchoDetectors.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ECHO_DETECTORS = TABS.register("echo_detectors",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tab.echo_detectors.echo_detectors"))
                    .icon(() -> new ItemStack(InitItems.ECHO_DETECTOR.get()))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(InitItems.METAL_DETECTOR.get());
                        pOutput.accept(InitItems.ECHO_DETECTOR.get());
                        pOutput.accept(InitItems.RESONANT_DETECTOR.get());

                        pOutput.accept(InitItems.IRON_FILTER.get());
                        pOutput.accept(InitItems.COPPER_FILTER.get());
                        pOutput.accept(InitItems.GOLD_FILTER.get());
                        pOutput.accept(InitItems.NETHERITE_FILTER.get());
                        pOutput.accept(InitItems.DIAMOND_FILTER.get());
                        pOutput.accept(InitItems.EMERALD_FILTER.get());
                        pOutput.accept(InitItems.AMETHYST_FILTER.get());
                        pOutput.accept(InitItems.QUARTZ_FILTER.get());
                        pOutput.accept(InitItems.COAL_FILTER.get());
                        pOutput.accept(InitItems.LAPIS_FILTER.get());
                        pOutput.accept(InitItems.REDSTONE_FILTER.get());


                        pOutput.accept(InitItems.DETECTOR_UPGRADE_SMITHING_TEMPLATE.get());

                        pOutput.accept(InitItems.LOCATION_UPGRADE.get());
                        pOutput.accept(InitItems.NAME_UPGRADE.get());

//                        pOutput.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(InitEnchantments.RANGE.get(), 1)));
//                        pOutput.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(InitEnchantments.RANGE.get(), 2)));
//                        pOutput.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(InitEnchantments.RANGE.get(), 3)));

                    })
                    .build());
}