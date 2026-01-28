package com.tortugolen.echo_detectors.Init;

import com.tortugolen.echo_detectors.EchoDetectors;
import com.tortugolen.echo_detectors.Items.EchoDetectorItem;
import com.tortugolen.echo_detectors.Items.MetalDetectorItem;
import com.tortugolen.echo_detectors.Items.ResonantDetectorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EchoDetectors.MOD_ID);

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector", () -> new MetalDetectorItem(new Item.Properties().durability(64), 16));
    public static final RegistryObject<Item> ECHO_DETECTOR = ITEMS.register("echo_detector", () -> new EchoDetectorItem(new Item.Properties().durability(128), 64));
    public static final RegistryObject<Item> RESONANT_DETECTOR = ITEMS.register("resonant_detector", () -> new ResonantDetectorItem(new Item.Properties().durability(128), 128));
    public static final RegistryObject<Item> DETECTOR_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("detector_upgrade_smithing_template", () -> new Item(new Item.Properties()));

    //Filters

    public static final RegistryObject<Item> IRON_FILTER = ITEMS.register("iron_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLD_FILTER = ITEMS.register("gold_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> COPPER_FILTER = ITEMS.register("copper_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> COAL_FILTER = ITEMS.register("coal_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LAPIS_FILTER = ITEMS.register("lapis_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> REDSTONE_FILTER = ITEMS.register("redstone_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> AMETHYST_FILTER = ITEMS.register("amethyst_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_FILTER = ITEMS.register("diamond_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> EMERALD_FILTER = ITEMS.register("emerald_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> QUARTZ_FILTER = ITEMS.register("quartz_filter", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_FILTER = ITEMS.register("netherite_filter", () -> new Item(new Item.Properties().stacksTo(1)));

    //Upgrades

    public static final RegistryObject<Item> LOCATION_UPGRADE = ITEMS.register("location_upgrade", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NAME_UPGRADE = ITEMS.register("name_upgrade", () -> new Item(new Item.Properties().stacksTo(1)));

}
