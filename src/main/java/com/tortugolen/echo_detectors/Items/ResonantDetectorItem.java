package com.tortugolen.echo_detectors.Items;

import com.tortugolen.echo_detectors.Compat.Ostrea.Ostrea;
import com.tortugolen.echo_detectors.Compat.Ostrea.OstreaInitItems;
import com.tortugolen.echo_detectors.Compat.Ostrea.OstreaValidBlocks;
import com.tortugolen.echo_detectors.GUIs.Menus.ResonantDetectorMenu;
import com.tortugolen.echo_detectors.Init.InitItems;
import com.tortugolen.echo_detectors.Init.InitSounds;
import com.tortugolen.echo_detectors.Init.InitTriggers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResonantDetectorItem extends MetalDetectorItem {
    public ResonantDetectorItem(Properties pProperties, int range) {
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

                if (isValidBlock(pStack, pTryBlock)) {
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
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide() && pPlayer.isShiftKeyDown()) {
            if (pPlayer instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((pId, pInventory, ppPlayer) -> new ResonantDetectorMenu(pId, pInventory, pStack, new SimpleContainerData(2)), Component.translatable("item.echo_detectors.resonant_detector")), pBuffer -> pBuffer.writeItem(pStack));
            }
            return InteractionResultHolder.success(pStack);
        }

        return InteractionResultHolder.pass(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        ItemStack storedItem = getStoredItem(pStack);
        if (!storedItem.isEmpty()) pTooltipComponents.add(Component.translatable("tooltip.echo_detectors.filters").withStyle(ChatFormatting.GRAY));
        if (!storedItem.isEmpty()) pTooltipComponents.add(Component.literal(" ").append(Component.translatable(getFilter(pStack)).withStyle(getFilterColor(pStack))));
        if (!storedItem.isEmpty()) pTooltipComponents.add(Component.literal(" "));
        if (hasUpgrades(pStack)) pTooltipComponents.add(Component.translatable("tooltip.echo_detectors.upgrades").withStyle(ChatFormatting.GRAY));
        if (hasLocationUpgrade(pStack)) pTooltipComponents.add(Component.literal(" ").append(Component.translatable("tooltip.echo_detectors.location").withStyle(ChatFormatting.YELLOW)));
        if (hasNameUpgrade(pStack)) pTooltipComponents.add(Component.literal(" ").append(Component.translatable("tooltip.echo_detectors.name").withStyle(ChatFormatting.YELLOW)));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.echo_detectors.hand").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.echo_detectors.range", range + getRangeBonus(pStack)).withStyle(ChatFormatting.DARK_GREEN));
        if (pStack.isEnchanted()) pTooltipComponents.add(Component.literal(" "));
    }

    public static ItemStack getStoredItem(ItemStack pStack) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (tag.contains("stored_item")) return ItemStack.of(tag.getCompound("stored_item"));
        return ItemStack.EMPTY;
    }

    public static void setStoredItem(ItemStack pStack, ItemStack pStoredItem) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (!pStoredItem.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            pStoredItem.save(itemTag);
            tag.put("stored_item", itemTag);
        } else {
            tag.remove("stored_item");
        }
    }

    private boolean isValidBlock(ItemStack pStack, Block pBlock) {
        ItemStack storedItem = getStoredItem(pStack);
        if (!storedItem.isEmpty()) {
            if (storedItem.is(InitItems.IRON_FILTER.get())) return isIronBlock(pBlock);
            if (storedItem.is(InitItems.GOLD_FILTER.get())) return isGoldBlock(pBlock);
            if (storedItem.is(InitItems.COPPER_FILTER.get())) return isCopperBlock(pBlock);
            if (storedItem.is(InitItems.COAL_FILTER.get())) return isCoalBlock(pBlock);
            if (storedItem.is(InitItems.LAPIS_FILTER.get())) return isLapisBlock(pBlock);
            if (storedItem.is(InitItems.REDSTONE_FILTER.get())) return isRedstoneBlock(pBlock);
            if (storedItem.is(InitItems.AMETHYST_FILTER.get())) return isAmethystBlock(pBlock);
            if (storedItem.is(InitItems.DIAMOND_FILTER.get())) return isDiamondBlock(pBlock);
            if (storedItem.is(InitItems.EMERALD_FILTER.get())) return isEmeraldBlock(pBlock);
            if (storedItem.is(InitItems.QUARTZ_FILTER.get())) return isQuartzBlock(pBlock);
            if (storedItem.is(InitItems.NETHERITE_FILTER.get())) return isNetheriteBlock(pBlock);

            if (Ostrea.isLoaded()) {
                if (storedItem.is(OstreaInitItems.ARAGONITE_FILTER.get())) return OstreaValidBlocks.isAragoniteBlock(pBlock);
            }
        }
        return isMetalBlock(pBlock);
    }

    private String getFilter(ItemStack pStack) {
        ItemStack storedItem = getStoredItem(pStack);
        if (!storedItem.isEmpty()) {
            if (storedItem.is(InitItems.IRON_FILTER.get())) return "tooltip.echo_detectors.iron";
            if (storedItem.is(InitItems.GOLD_FILTER.get())) return "tooltip.echo_detectors.gold";
            if (storedItem.is(InitItems.COPPER_FILTER.get())) return "tooltip.echo_detectors.copper";
            if (storedItem.is(InitItems.COAL_FILTER.get())) return "tooltip.echo_detectors.coal";
            if (storedItem.is(InitItems.LAPIS_FILTER.get())) return "tooltip.echo_detectors.lapis";
            if (storedItem.is(InitItems.REDSTONE_FILTER.get())) return "tooltip.echo_detectors.redstone";
            if (storedItem.is(InitItems.AMETHYST_FILTER.get())) return "tooltip.echo_detectors.amethyst";
            if (storedItem.is(InitItems.DIAMOND_FILTER.get())) return "tooltip.echo_detectors.diamond";
            if (storedItem.is(InitItems.EMERALD_FILTER.get())) return "tooltip.echo_detectors.emerald";
            if (storedItem.is(InitItems.QUARTZ_FILTER.get())) return "tooltip.echo_detectors.quartz";
            if (storedItem.is(InitItems.NETHERITE_FILTER.get())) return "tooltip.echo_detectors.netherite";

            if (Ostrea.isLoaded()) {
                if (storedItem.is(OstreaInitItems.ARAGONITE_FILTER.get())) return "tooltip.echo_detectors.aragonite";
            }
        }
        return "tooltip.echo_detectors.255";
    }

    private Style getTrimStyle(ResourceKey<TrimMaterial> pKey) {
        return Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).get(pKey).description().getStyle();
    }

    private Style getFilterColor(ItemStack pStack) {
        ItemStack storedItem = getStoredItem(pStack);
        if (!storedItem.isEmpty()) {
            if (storedItem.is(InitItems.IRON_FILTER.get())) return getTrimStyle(TrimMaterials.IRON);
            if (storedItem.is(InitItems.GOLD_FILTER.get())) return getTrimStyle(TrimMaterials.GOLD);
            if (storedItem.is(InitItems.COPPER_FILTER.get())) return getTrimStyle(TrimMaterials.COPPER);
            if (storedItem.is(InitItems.COAL_FILTER.get())) return Style.EMPTY.withColor(ChatFormatting.DARK_GRAY);
            if (storedItem.is(InitItems.LAPIS_FILTER.get())) return getTrimStyle(TrimMaterials.LAPIS);
            if (storedItem.is(InitItems.REDSTONE_FILTER.get())) return getTrimStyle(TrimMaterials.REDSTONE);
            if (storedItem.is(InitItems.AMETHYST_FILTER.get())) return getTrimStyle(TrimMaterials.AMETHYST);
            if (storedItem.is(InitItems.DIAMOND_FILTER.get())) return getTrimStyle(TrimMaterials.DIAMOND);
            if (storedItem.is(InitItems.EMERALD_FILTER.get())) return getTrimStyle(TrimMaterials.EMERALD);
            if (storedItem.is(InitItems.QUARTZ_FILTER.get())) return getTrimStyle(TrimMaterials.QUARTZ);
            if (storedItem.is(InitItems.NETHERITE_FILTER.get())) return getTrimStyle(TrimMaterials.NETHERITE);

            if (Ostrea.isLoaded()) {
                if (storedItem.is(OstreaInitItems.ARAGONITE_FILTER.get())) return getTrimStyle(TrimMaterials.COPPER);
            }
        }
        return Style.EMPTY.withColor(ChatFormatting.GRAY);
    }
}
