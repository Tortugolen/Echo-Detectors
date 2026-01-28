package com.tortugolen.echo_detectors.Items;

import com.tortugolen.echo_detectors.Base.ValidBlocks;
import com.tortugolen.echo_detectors.Init.InitSounds;
import com.tortugolen.echo_detectors.Init.InitTriggers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MetalDetectorItem extends AbstractDetectorItem implements ValidBlocks {
    public MetalDetectorItem(Properties pProperties, int range) {
        super(pProperties, range);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        ItemStack pStack = pContext.getItemInHand();
        InteractionHand pUsedHand = pContext.getHand();
        Player pPlayer = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        BlockPos pClickedPos = pContext.getClickedPos();

        if (!pLevel.isClientSide()) {
            BlockPos pPos = null;
            Block pBlock = null;
            boolean validBlockFound = false;

            for (int i = 0; i < (range + getRangeBonus(pStack)); i++) {
                BlockPos pTryPos = pClickedPos.below(i);
                Block pTryBlock = pLevel.getBlockState(pTryPos).getBlock();

                if (isMetalBlock(pTryBlock)) {
                    pPos = pTryPos;
                    pBlock = pTryBlock;
                    validBlockFound = true;
                    break;
                }
            }

            if (!validBlockFound) {
                pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), InitSounds.FAILED_TRY.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
                pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), InitSounds.FOUND.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                sendDetectionMessage(pStack, pPlayer, pPos, pBlock);
                if (pPlayer instanceof ServerPlayer pServerPlayer) {
                    InitTriggers.ABSTRACT0.trigger(pServerPlayer);
                }
            }

            if (!pPlayer.isCreative()) {
                pStack.hurtAndBreak(1, pPlayer, player -> player.broadcastBreakEvent(pUsedHand));
                pPlayer.getCooldowns().addCooldown(this, 80);
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (hasUpgrades(pStack)) pTooltipComponents.add(Component.translatable("tooltip.echo_detectors.upgrades").withStyle(ChatFormatting.GRAY));
        if (hasLocationUpgrade(pStack)) pTooltipComponents.add(Component.literal(" ").append(Component.translatable("tooltip.echo_detectors.location").withStyle(ChatFormatting.YELLOW)));
        if (hasNameUpgrade(pStack)) pTooltipComponents.add(Component.literal(" ").append(Component.translatable("tooltip.echo_detectors.name").withStyle(ChatFormatting.YELLOW)));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.echo_detectors.hand").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.echo_detectors.range", range + getRangeBonus(pStack)).withStyle(ChatFormatting.DARK_GREEN));
        if (pStack.isEnchanted()) pTooltipComponents.add(Component.literal(" "));
    }

    protected void sendDetectionMessage(ItemStack pStack, Player pPlayer, BlockPos pPos, Block pBlock) {
        if (hasUpgrades(pStack)) {
            if (hasLocationUpgrade(pStack) && !hasNameUpgrade(pStack)) {
                pPlayer.sendSystemMessage(Component.translatable("chat.echo_detectors.found").append(Component.translatable("chat.echo_detectors.block")).append(Component.translatable("chat.echo_detectors.at"))
                        .append(Component.translatable("chat.echo_detectors.x")).append(Component.literal(String.valueOf(pPos.getX())).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("chat.echo_detectors.,"))
                        .append(Component.translatable("chat.echo_detectors.y")).append(Component.literal(String.valueOf(pPos.getY())).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("chat.echo_detectors.,"))
                        .append(Component.translatable("chat.echo_detectors.z")).append(Component.literal(String.valueOf(pPos.getZ())).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("chat.echo_detectors.."))
                );
            }
            if (!hasLocationUpgrade(pStack) && hasNameUpgrade(pStack)) {
                pPlayer.sendSystemMessage(Component.translatable("chat.echo_detectors.found").append(pBlock.getName().copy().withStyle(ChatFormatting.YELLOW)));
            }
            if (hasLocationUpgrade(pStack) && hasNameUpgrade(pStack)) {
                pPlayer.sendSystemMessage(Component.translatable("chat.echo_detectors.found").append(pBlock.getName().copy().withStyle(ChatFormatting.YELLOW)).append(Component.translatable("chat.echo_detectors. at"))
                        .append(Component.translatable("chat.echo_detectors.x")).append(Component.literal(String.valueOf(pPos.getX())).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("chat.echo_detectors.,"))
                        .append(Component.translatable("chat.echo_detectors.y")).append(Component.literal(String.valueOf(pPos.getY())).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("chat.echo_detectors.,"))
                        .append(Component.translatable("chat.echo_detectors.z")).append(Component.literal(String.valueOf(pPos.getZ())).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("chat.echo_detectors.."))
                );
            }
        }
    }
}
