package com.tortugolen.ostrea.Init;

import com.tortugolen.ostrea.Enchantments.PassiveAnnulmentEnchantment;
import com.tortugolen.ostrea.Enchantments.ReductionEnchantment;
import com.tortugolen.ostrea.Enchantments.SelectiveBlessingEnchantment;
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

    public static final EnchantmentCategory SHELLFISH_KNIFE = EnchantmentCategory.create("shellfish_knife", (item -> item == InitItems.PEARL_NECKLACE.get()));
    public static final EnchantmentCategory PEARL_NECKLACE = EnchantmentCategory.create("pearl_necklace", (item -> item == InitItems.PEARL_NECKLACE.get()));

    public static RegistryObject<Enchantment> SHELL_OPENER = ENCHANTMENTS.register("shell_opener",
            () -> new ShellOpenerEnchantment(Enchantment.Rarity.RARE, SHELLFISH_KNIFE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static RegistryObject<Enchantment> SELECTIVE_BLESSING = ENCHANTMENTS.register("selective_blessing",
            () -> new SelectiveBlessingEnchantment(Enchantment.Rarity.VERY_RARE, PEARL_NECKLACE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST));
    public static RegistryObject<Enchantment> REDUCTION = ENCHANTMENTS.register("reduction",
            () -> new ReductionEnchantment(Enchantment.Rarity.RARE, PEARL_NECKLACE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST));
    public static RegistryObject<Enchantment> PASSIVE_ANNULMENT = ENCHANTMENTS.register("passive_annulment",
            () -> new PassiveAnnulmentEnchantment(Enchantment.Rarity.RARE, PEARL_NECKLACE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST));
}
