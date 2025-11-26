package com.tortugolen.ostrea.Items;

import com.tortugolen.ostrea.Init.InitEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Map;

public class PearlNecklaceItem extends Item {
    private int STORED_EFFECTS = 0;

    public PearlNecklaceItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
        return armorType == EquipmentSlot.HEAD || armorType == EquipmentSlot.CHEST;
    }

    private int getUnbreakingLevel(ItemStack pStack) {
        return pStack.getEnchantmentLevel(Enchantments.UNBREAKING);
    }

    private int getReductionLevel(ItemStack pStack) {
        return pStack.getEnchantmentLevel(InitEnchantments.REDUCTION.get());
    }

    private int getItemDamageWithAmplifier(ItemStack pStack) {
        if (getUnbreakingLevel(pStack) == 0) return 16;
        if (getUnbreakingLevel(pStack) == 1) return 8;
        if (getUnbreakingLevel(pStack) == 2) return 4;
        if (getUnbreakingLevel(pStack) == 3) return 2;
        else return 16;
    }

    private int getInventoryUseTime(ItemStack pStack) {
        int passiveAnnulmentLevel = pStack.getEnchantmentLevel(InitEnchantments.PASSIVE_ANNULMENT.get());
        if (passiveAnnulmentLevel == 1) return 60;
        if (passiveAnnulmentLevel == 2) return 30;
        if (passiveAnnulmentLevel == 3) return 15;
        if (passiveAnnulmentLevel == 4) return 0;
        if (passiveAnnulmentLevel == 5) return 0;
        else return 72000 * 24;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);

        var activeEffectsList = pPlayer.getActiveEffects().stream().toList();
        if (activeEffectsList.isEmpty()) return InteractionResultHolder.fail(pStack);

        var harmfulEffectsList = activeEffectsList.stream().filter(e -> !e.getEffect().isBeneficial()).toList();

        Map<Enchantment, Integer> enchantments = pStack.getAllEnchantments();

        boolean hasSelectiveBlessing = enchantments.containsKey(InitEnchantments.SELECTIVE_BLESSING.get());
        boolean hasReduction = enchantments.containsKey(InitEnchantments.REDUCTION.get());
        boolean hasPassiveAnnulment = enchantments.containsKey(InitEnchantments.PASSIVE_ANNULMENT.get());

        MobEffectInstance selectedEffectInstance;

        if (hasSelectiveBlessing) {
            if (harmfulEffectsList.isEmpty()) return InteractionResultHolder.fail(pStack);
            selectedEffectInstance = harmfulEffectsList.get(pLevel.random.nextInt(harmfulEffectsList.size()));
        } else {
            selectedEffectInstance = activeEffectsList.get(pLevel.random.nextInt(activeEffectsList.size()));
        }

        MobEffect effect = selectedEffectInstance.getEffect();
        int duration = selectedEffectInstance.getDuration();
        int amplifier = selectedEffectInstance.getAmplifier();

        int itemDamageWithoutAmplifier = 20 * (getUnbreakingLevel(pStack) + 1);

        var remaining = new java.util.ArrayList<MobEffectInstance>();
        boolean removed = false;

        for (MobEffectInstance inst : activeEffectsList) {
            if (!removed && inst.getEffect() == effect && inst.getDuration() == duration && inst.getAmplifier() == amplifier) {
                removed = true;
                continue;
            }
            remaining.add(inst);
        }

        pPlayer.removeAllEffects();
        for (MobEffectInstance inst : remaining) {
            pPlayer.addEffect(new MobEffectInstance(inst));
        }

        if (!removed) return InteractionResultHolder.fail(pStack);

        if (amplifier > 0) {
            int reduction = hasReduction ? (getReductionLevel(pStack) + 1) : 1;
            int newAmp = amplifier - reduction;
            if (newAmp >= 0) {
                pPlayer.addEffect(new MobEffectInstance(effect, duration, newAmp));
            }
        }

        if (!pPlayer.isCreative()) {
            if (amplifier == 0)
                pStack.hurtAndBreak(duration / itemDamageWithoutAmplifier, pPlayer, pl -> pl.broadcastBreakEvent(pUsedHand));
            else
                pStack.hurtAndBreak(getItemDamageWithAmplifier(pStack), pPlayer, pl -> pl.broadcastBreakEvent(pUsedHand));
        }

        return InteractionResultHolder.success(pStack);
    }
}
