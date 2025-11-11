package com.tortugolen.ostrea.Init;

import com.tortugolen.ostrea.Ostrea;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Ostrea.MOD_ID);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Ostrea.MOD_ID, name)));
    }

    public static final RegistryObject<SoundEvent> PEARL_TTP_BREAK = registerSoundEvents("pearl_tip_break");
    public static final RegistryObject<SoundEvent> OYSTER_HIT = registerSoundEvents("oyster_hit");

}
