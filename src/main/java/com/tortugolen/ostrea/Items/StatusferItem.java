package com.tortugolen.ostrea.Items;

import com.tortugolen.ostrea.Init.InitItems;
import com.tortugolen.ostrea.Init.InitSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StatusferItem extends Item {
    private static final String EFFECT = "effect";

    public StatusferItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);
        CompoundTag tag = pStack.getOrCreateTag();

        ResourceLocation effectId = ResourceLocation.tryParse(tag.getString(EFFECT));
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(effectId);

        if (effectId == null) {
            if (!pPlayer.isCreative()) {
                pStack.hurtAndBreak(64, pPlayer, player -> {
                    player.broadcastBreakEvent(pUsedHand);

                    if (!player.getInventory().add(InitItems.RECEPTACLE_PEARL.get().getDefaultInstance())) {
                        player.drop(InitItems.RECEPTACLE_PEARL.get().getDefaultInstance(), false);
                    }
                });
            }

            return InteractionResultHolder.pass(pStack);
        }
        if (effect == null) {
            if (!pPlayer.isCreative()) {
                pStack.hurtAndBreak(64, pPlayer, player -> {
                    player.broadcastBreakEvent(pUsedHand);

                    if (!player.getInventory().add(InitItems.RECEPTACLE_PEARL.get().getDefaultInstance())) {
                        player.drop(InitItems.RECEPTACLE_PEARL.get().getDefaultInstance(), false);
                    }
                });
            }

            return InteractionResultHolder.pass(pStack);
        }

        if (!pPlayer.hasEffect(effect)) {
            pPlayer.addEffect(new MobEffectInstance(effect, 1200, 0));
        } else {
            pPlayer.addEffect(new MobEffectInstance(effect, pPlayer.getEffect(effect).getDuration() + 1200, 0));
        }

        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), InitSounds.EFFECT_CLEAR.get(), SoundSource.PLAYERS, 1F, 1F);

        if (!pPlayer.isCreative()) {
            pStack.hurtAndBreak(16, pPlayer, player -> {
                player.broadcastBreakEvent(pUsedHand);

                if (!player.getInventory().add(InitItems.RECEPTACLE_PEARL.get().getDefaultInstance())) {
                    player.drop(InitItems.RECEPTACLE_PEARL.get().getDefaultInstance(), false);
                }
            });
        }

        return InteractionResultHolder.success(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();

        if (tag == null) return;

        String effectId = tag.getString(EFFECT);
        MobEffect pEffect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(effectId));

        if (pEffect == null) return;

        pTooltipComponents.add(Component.empty().append(pEffect.getDisplayName().copy().withStyle(style -> style.withColor(pEffect.getColor()))));
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }
}
