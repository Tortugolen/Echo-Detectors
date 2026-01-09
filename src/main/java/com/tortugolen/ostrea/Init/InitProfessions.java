package com.tortugolen.ostrea.Init;

import com.google.common.collect.ImmutableSet;
import com.tortugolen.ostrea.Ostrea;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitProfessions {
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Ostrea.MOD_ID);

    public static final RegistryObject<VillagerProfession> JEWELSMITH = PROFESSIONS.register("jewelsmith",
            () -> new VillagerProfession("jewelsmith", holder -> true, holder -> true,
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_TOOLSMITH));
}
