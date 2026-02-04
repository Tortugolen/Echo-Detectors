package com.tortugolen.echo_detectors.Compat.Ostrea;

import com.tortugolen.echo_detectors.EchoDetectors;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OstreaInitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EchoDetectors.MOD_ID);

    public static final RegistryObject<Item> ARAGONITE_FILTER = ITEMS.register("aragonite_filter", () -> new Item(new Item.Properties().stacksTo(1)));
}
