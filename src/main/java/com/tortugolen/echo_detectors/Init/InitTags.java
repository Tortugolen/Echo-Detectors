package com.tortugolen.echo_detectors.Init;

import com.tortugolen.echo_detectors.EchoDetectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class InitTags {
    public static class Items {
        private static TagKey<Item> ItemTag(String id) {
            return ItemTags.create(new ResourceLocation(EchoDetectors.MOD_ID, id));
        }

        public static final TagKey<Item> FILTERS = ItemTag("filters");
    }
}
