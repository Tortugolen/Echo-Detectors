package com.tortugolen.echo_detectors.Init;

import com.tortugolen.echo_detectors.EchoDetectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EchoDetectors.MOD_ID);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(EchoDetectors.MOD_ID, name)));
    }

    public static final RegistryObject<SoundEvent> FAILED_TRY = registerSoundEvents("failed_try");
    public static final RegistryObject<SoundEvent> FOUND = registerSoundEvents("found");

}
