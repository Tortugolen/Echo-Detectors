package com.tortugolen.echo_detectors.Init;

import com.tortugolen.echo_detectors.EchoDetectors;
import com.tortugolen.echo_detectors.Enchantments.RangeEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EchoDetectors.MOD_ID);

    public static final EnchantmentCategory DETECTOR = EnchantmentCategory.create("detector", (item -> {
        String itemClass = item.getClass().getName();
        return itemClass.contains("MetalDetectorItem") || itemClass.contains("EchoDetectorItem") || itemClass.contains("ResonantDetectorItem");
    }));
    public static final EnchantmentCategory RESONANT_DETECTOR = EnchantmentCategory.create("resonant_detector", (item -> item == InitItems.RESONANT_DETECTOR.get()));

    public static RegistryObject<Enchantment> RANGE = ENCHANTMENTS.register("range",
            () -> new RangeEnchantment(Enchantment.Rarity.UNCOMMON, RESONANT_DETECTOR, EquipmentSlot.MAINHAND));
}
