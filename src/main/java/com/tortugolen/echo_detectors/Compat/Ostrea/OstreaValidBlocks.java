package com.tortugolen.echo_detectors.Compat.Ostrea;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class OstreaValidBlocks {
    private static Block ARAGONITE_SHARD = null;
    private static Block ARAGONITE_CLUSTER = null;

    static {
        if (Ostrea.isLoaded()) {
            ARAGONITE_SHARD = BuiltInRegistries.BLOCK.get(new ResourceLocation(Ostrea.MOD_ID, "aragonite_shard"));
            ARAGONITE_CLUSTER = BuiltInRegistries.BLOCK.get(new ResourceLocation(Ostrea.MOD_ID, "aragonite_cluster"));
        }
    }

    public static boolean isNonMetalBlock(Block pBlock) {
        return isAragoniteBlock(pBlock);
    }

    public static boolean isCrystalMineral(Block pBlock) {
        return isAragoniteBlock(pBlock);
    }

    public static boolean isAragoniteBlock(Block pBlock) {
        if (!Ostrea.isLoaded()) return false;
        return pBlock == ARAGONITE_SHARD || pBlock == ARAGONITE_CLUSTER;
    }
}