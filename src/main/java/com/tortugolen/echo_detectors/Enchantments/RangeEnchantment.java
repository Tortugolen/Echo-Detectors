package com.tortugolen.echo_detectors.Enchantments;

import com.tortugolen.echo_detectors.Items.AbstractDetectorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class RangeEnchantment extends Enchantment {
    public RangeEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pEquipmentSlots) {
        super(pRarity, pCategory, pEquipmentSlots);
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof AbstractDetectorItem;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack pStack) {
        return pStack.getItem() instanceof AbstractDetectorItem;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}