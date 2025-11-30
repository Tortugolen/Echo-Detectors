package com.tortugolen.ostrea.Items;

import com.tortugolen.ostrea.Init.InitEnchantments;
import com.tortugolen.ostrea.Init.InitSounds;
import com.tortugolen.ostrea.Init.InitTriggers;
import com.tortugolen.ostrea.Triggers.EffectRemovedTrigger;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.Map;

public class PearlNecklaceItem extends Item {
    private int STORED_EFFECTS = 0;

    private int PARTICLE_NUMBER = 50;
    private int HORIZONTAL_DISPERSION = 2;
    private double VERTICAL_DISPERSION = 0.3;
    private int INITIAL_POSITION = 1;

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
        int color = effect.getColor();

        int itemDamageWithoutAmplifier = 20 * (getUnbreakingLevel(pStack) + 1);

        var remaining = new ArrayList<MobEffectInstance>();
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

        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), InitSounds.EFFECT_CLEAR.get(), SoundSource.PLAYERS, 1F, 1F);

        float r = ((color >> 16) & 0xFF) / 255F;
        float g = ((color >> 8) & 0xFF) / 255F;
        float b = (color & 0xFF) / 255F;

        for (int i = 0; i < PARTICLE_NUMBER; i++) {
            double ox = (pLevel.random.nextDouble() - 0.5) * HORIZONTAL_DISPERSION;
            double oy = (pLevel.random.nextDouble() - 0.2) * VERTICAL_DISPERSION;
            double oz = (pLevel.random.nextDouble() - 0.5) * HORIZONTAL_DISPERSION;

            pLevel.addParticle(ParticleTypes.ENTITY_EFFECT, pPlayer.getX() + ox, pPlayer.getY() + INITIAL_POSITION + oy, pPlayer.getZ() + oz, r, g, b);
        }

        if (amplifier > 0) {
            int reduction = hasReduction ? (getReductionLevel(pStack) + 1) : 1;
            int newAmp = amplifier - reduction;
            if (newAmp >= 0) {
                pPlayer.addEffect(new MobEffectInstance(effect, duration, newAmp));
            }
        }

        if (!pPlayer.isCreative()) {
            if (amplifier == 0) {
                pStack.hurtAndBreak(duration / itemDamageWithoutAmplifier, pPlayer, player -> player.broadcastBreakEvent(pUsedHand));
            }
            else {
                pStack.hurtAndBreak(getItemDamageWithAmplifier(pStack), pPlayer, player -> player.broadcastBreakEvent(pUsedHand));
            }
        }

        if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer serverPlayer) {
            InitTriggers.EFFECT_REMOVED.trigger(serverPlayer, pStack, effect);
        }

        return InteractionResultHolder.success(pStack);
    }
}
