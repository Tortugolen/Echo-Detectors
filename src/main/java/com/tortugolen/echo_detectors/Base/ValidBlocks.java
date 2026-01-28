package com.tortugolen.echo_detectors.Base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public interface ValidBlocks {
    default boolean isMetalBlock(Block pBlock) {
        return isMetalOreBlock(pBlock) || isIronBlock(pBlock) || isGoldBlock(pBlock) || isCopperBlock(pBlock) || isNetheriteBlock(pBlock);
    }

    default boolean isNonMetalBlock(Block pBlock) {
        return isCoalBlock(pBlock) || isLapisBlock(pBlock) || isRedstoneBlock(pBlock) ||
                isAmethystBlock(pBlock) || isDiamondBlock(pBlock) || isEmeraldBlock(pBlock) ||
                isQuartzBlock(pBlock) || isNetheriteBlock(pBlock);
    }

    default boolean isMetalMineral(Block pBlock) {
        return isIronBlock(pBlock) || isGoldBlock(pBlock) || isCopperBlock(pBlock) || isNetherMineral(pBlock);
    }

    default boolean isStoneMineral(Block pBlock) {
        return isCoalBlock(pBlock) || isLapisBlock(pBlock) || isRedstoneBlock(pBlock);
    }

    default boolean isCrystalMineral(Block pBlock) {
        return isAmethystBlock(pBlock) || isDiamondBlock(pBlock) || isEmeraldBlock(pBlock) || isQuartzBlock(pBlock);
    }

    default boolean isNetherMineral(Block pBlock) {
        return isQuartzBlock(pBlock) || isNetheriteBlock(pBlock);
    }

    default boolean isOreBlock(Block pBlock) {
        return isMetalOreBlock(pBlock) || isNonMetalOreBlock(pBlock);
    }

    default boolean isMetalOreBlock(Block pBlock) {
        return pBlock == Blocks.IRON_ORE || pBlock == Blocks.DEEPSLATE_IRON_ORE || pBlock == Blocks.RAW_IRON_BLOCK ||
                pBlock == Blocks.GOLD_ORE || pBlock == Blocks.DEEPSLATE_GOLD_ORE || pBlock == Blocks.RAW_GOLD_BLOCK || pBlock == Blocks.NETHER_GOLD_ORE ||
                pBlock == Blocks.COPPER_ORE || pBlock == Blocks.DEEPSLATE_COPPER_ORE || pBlock == Blocks.RAW_COPPER_BLOCK;
    }

    default boolean isNonMetalOreBlock(Block pBlock) {
        return pBlock == Blocks.COAL_ORE || pBlock == Blocks.DEEPSLATE_COAL_ORE ||
                pBlock == Blocks.LAPIS_ORE || pBlock == Blocks.DEEPSLATE_LAPIS_ORE ||
                pBlock == Blocks.REDSTONE_ORE || pBlock == Blocks.DEEPSLATE_REDSTONE_ORE ||
                pBlock == Blocks.SMALL_AMETHYST_BUD || pBlock == Blocks.MEDIUM_AMETHYST_BUD || pBlock == Blocks.LARGE_AMETHYST_BUD || pBlock == Blocks.AMETHYST_CLUSTER ||
                pBlock == Blocks.DIAMOND_ORE || pBlock == Blocks.DEEPSLATE_DIAMOND_ORE ||
                pBlock == Blocks.EMERALD_ORE || pBlock == Blocks.DEEPSLATE_EMERALD_ORE ||
                pBlock == Blocks.NETHER_QUARTZ_ORE || pBlock == Blocks.ANCIENT_DEBRIS;
    }

    default boolean isIronBlock(Block pBlock) {
        return pBlock == Blocks.IRON_ORE || pBlock == Blocks.DEEPSLATE_IRON_ORE || pBlock == Blocks.RAW_IRON_BLOCK ||
                pBlock == Blocks.IRON_BLOCK || pBlock == Blocks.IRON_BARS || pBlock == Blocks.IRON_DOOR || pBlock == Blocks.IRON_TRAPDOOR;
    }

    default boolean isGoldBlock(Block pBlock) {
        return pBlock == Blocks.GOLD_ORE || pBlock == Blocks.DEEPSLATE_GOLD_ORE || pBlock == Blocks.RAW_GOLD_BLOCK || pBlock == Blocks.NETHER_GOLD_ORE ||
                pBlock == Blocks.GOLD_BLOCK || pBlock == Blocks.GILDED_BLACKSTONE;
    }

    default boolean isCopperBlock(Block pBlock) {
        return pBlock == Blocks.COPPER_ORE || pBlock == Blocks.DEEPSLATE_COPPER_ORE || pBlock == Blocks.RAW_COPPER_BLOCK ||
                pBlock == Blocks.COPPER_BLOCK || pBlock == Blocks.WAXED_COPPER_BLOCK ||
                pBlock == Blocks.EXPOSED_COPPER || pBlock == Blocks.WAXED_EXPOSED_COPPER ||
                pBlock == Blocks.WEATHERED_COPPER || pBlock == Blocks.WAXED_WEATHERED_COPPER ||
                pBlock == Blocks.OXIDIZED_COPPER || pBlock == Blocks.WAXED_OXIDIZED_COPPER ||

                pBlock == Blocks.CUT_COPPER || pBlock == Blocks.WAXED_CUT_COPPER ||
                pBlock == Blocks.EXPOSED_CUT_COPPER || pBlock == Blocks.WAXED_EXPOSED_CUT_COPPER ||
                pBlock == Blocks.WEATHERED_CUT_COPPER || pBlock == Blocks.WAXED_WEATHERED_CUT_COPPER ||
                pBlock == Blocks.OXIDIZED_CUT_COPPER || pBlock == Blocks.WAXED_OXIDIZED_CUT_COPPER ||

                pBlock == Blocks.CUT_COPPER_SLAB || pBlock == Blocks.WAXED_CUT_COPPER_SLAB ||
                pBlock == Blocks.EXPOSED_CUT_COPPER_SLAB || pBlock == Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB ||
                pBlock == Blocks.WEATHERED_CUT_COPPER_SLAB || pBlock == Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB ||
                pBlock == Blocks.OXIDIZED_CUT_COPPER_SLAB || pBlock == Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB ||

                pBlock == Blocks.CUT_COPPER_STAIRS || pBlock == Blocks.WAXED_CUT_COPPER_STAIRS ||
                pBlock == Blocks.EXPOSED_CUT_COPPER_STAIRS || pBlock == Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS ||
                pBlock == Blocks.WEATHERED_CUT_COPPER_STAIRS || pBlock == Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS ||
                pBlock == Blocks.OXIDIZED_CUT_COPPER_STAIRS || pBlock == Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS;
    }

    default boolean isCoalBlock(Block pBlock) {
        return pBlock == Blocks.COAL_ORE || pBlock == Blocks.DEEPSLATE_COAL_ORE || pBlock == Blocks.COAL_BLOCK;
    }

    default boolean isLapisBlock(Block pBlock) {
        return pBlock == Blocks.LAPIS_ORE || pBlock == Blocks.DEEPSLATE_LAPIS_ORE || pBlock == Blocks.LAPIS_BLOCK;
    }

    default boolean isRedstoneBlock(Block pBlock) {
        return pBlock == Blocks.REDSTONE_ORE || pBlock == Blocks.DEEPSLATE_REDSTONE_ORE || pBlock == Blocks.REDSTONE_BLOCK;
    }

    default boolean isAmethystBlock(Block pBlock) {
        return pBlock == Blocks.SMALL_AMETHYST_BUD || pBlock == Blocks.MEDIUM_AMETHYST_BUD || pBlock == Blocks.LARGE_AMETHYST_BUD ||
                pBlock == Blocks.AMETHYST_CLUSTER || pBlock == Blocks.AMETHYST_BLOCK || pBlock == Blocks.BUDDING_AMETHYST;
    }

    default boolean isDiamondBlock(Block pBlock) {
        return pBlock == Blocks.DIAMOND_ORE || pBlock == Blocks.DEEPSLATE_DIAMOND_ORE || pBlock == Blocks.DIAMOND_BLOCK;
    }

    default boolean isEmeraldBlock(Block pBlock) {
        return pBlock == Blocks.EMERALD_ORE || pBlock == Blocks.DEEPSLATE_EMERALD_ORE || pBlock == Blocks.EMERALD_BLOCK;
    }

    default boolean isQuartzBlock(Block pBlock) {
        return pBlock == Blocks.NETHER_QUARTZ_ORE || pBlock == Blocks.QUARTZ_BLOCK ||
                pBlock == Blocks.QUARTZ_BRICKS || pBlock == Blocks.QUARTZ_PILLAR || pBlock == Blocks.CHISELED_QUARTZ_BLOCK || pBlock == Blocks.SMOOTH_QUARTZ;
    }

    default boolean isNetheriteBlock(Block pBlock) {
        return pBlock == Blocks.ANCIENT_DEBRIS || pBlock == Blocks.NETHERITE_BLOCK;
    }
}