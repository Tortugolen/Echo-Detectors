package com.tortugolen.echo_detectors.Items;

import com.tortugolen.echo_detectors.Init.InitEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AbstractDetectorItem extends Item {
    public final int range;
    public static final String UPGRADES = "upgrades";
    public static final String LOCATION_UPGRADE = "location_upgrade";
    public static final String NAME_UPGRADE = "name_upgrade";

    public AbstractDetectorItem(Properties pProperties, int range) {
        super(pProperties);
        this.range = range;
    }

    public int getRangeEnchantmentLevel(ItemStack pStack) {
        return pStack.getEnchantmentLevel(InitEnchantments.RANGE.get());
    }

    public int getRangeBonus(ItemStack pStack) {
        if (getRangeEnchantmentLevel(pStack) == 0) return 0;
        if (getRangeEnchantmentLevel(pStack) == 1) return 16;
        if (getRangeEnchantmentLevel(pStack) == 2) return 32;
        if (getRangeEnchantmentLevel(pStack) == 3) return 64;
        return 0;
    }

    public static boolean hasUpgrades(ItemStack pStack) {
        return hasLocationUpgrade(pStack) ||hasNameUpgrade(pStack);
    }

    public static void removeAllUpgrades(ItemStack detector) {
        CompoundTag tag = detector.getOrCreateTag();
        tag.remove(UPGRADES);
    }

    public static void copyUpgrades(ItemStack from, ItemStack to) {
        CompoundTag fromTag = from.getOrCreateTag();
        if (!fromTag.contains(UPGRADES)) return;

        CompoundTag toTag = to.getOrCreateTag();
        CompoundTag upgrades = fromTag.getCompound(UPGRADES).copy();
        toTag.put(UPGRADES, upgrades);
    }

    public static void applyLocationUpgrade(ItemStack detector) {
        CompoundTag tag = detector.getOrCreateTag();
        CompoundTag upgrades = tag.getCompound(UPGRADES);
        upgrades.putBoolean(LOCATION_UPGRADE, true);
        tag.put(UPGRADES, upgrades);
    }

    public static boolean hasLocationUpgrade(ItemStack detector) {
        CompoundTag tag = detector.getOrCreateTag();
        if (!tag.contains(UPGRADES)) return false;
        CompoundTag upgrades = tag.getCompound(UPGRADES);
        return upgrades.getBoolean(LOCATION_UPGRADE);
    }

    public static void applyNameUpgrade(ItemStack detector) {
        CompoundTag tag = detector.getOrCreateTag();
        CompoundTag upgrades = tag.getCompound(UPGRADES);
        upgrades.putBoolean(NAME_UPGRADE, true);
        tag.put(UPGRADES, upgrades);
    }

    public static boolean hasNameUpgrade(ItemStack detector) {
        CompoundTag tag = detector.getOrCreateTag();
        if (!tag.contains(UPGRADES)) return false;
        CompoundTag upgrades = tag.getCompound(UPGRADES);
        return upgrades.getBoolean(NAME_UPGRADE);
    }
}