package com.tortugolen.ostrea.Init;

import com.google.common.collect.ImmutableSet;
import com.tortugolen.ostrea.Ostrea;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitPois {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Ostrea.MOD_ID);

}
