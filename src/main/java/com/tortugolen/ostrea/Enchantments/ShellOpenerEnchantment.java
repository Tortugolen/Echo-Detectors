package com.tortugolen.ostrea.Enchantments;

import com.tortugolen.ostrea.Init.InitItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Set;

public class ShellOpenerEnchantment extends Enchantment {
    public ShellOpenerEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pEquipmentSlots) {
        super(pRarity, pCategory, pEquipmentSlots);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        if (stack.getItem() == InitItems.SHELLFISH_KNIFE.get()) {
            return false;
        }
        if (stack.getItem() instanceof SwordItem) {
            return true;
        }
        return super.canEnchant(stack);
    }

    public boolean allowedInCreativeTab(ItemStack book, Set<EnchantmentCategory> allowedCategories) {
        return this.isAllowedOnBooks() && allowedCategories.contains(this.category);
    }

    public ItemStack getEnchantmentItem() {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }

    public boolean isTreasureOnly() {
        return true;
    }
}
