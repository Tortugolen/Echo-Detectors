package com.tortugolen.ostrea.Init;

import com.tortugolen.ostrea.Enchantments.ShellOpenerEnchantment;
import com.tortugolen.ostrea.Ostrea;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Ostrea.MOD_ID);

    public static RegistryObject<Enchantment> SHELL_OPENER = ENCHANTMENTS.register("shell_opener",
            () -> new ShellOpenerEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
}
