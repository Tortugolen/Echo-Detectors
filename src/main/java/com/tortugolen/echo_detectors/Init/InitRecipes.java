package com.tortugolen.echo_detectors.Init;

import com.tortugolen.echo_detectors.EchoDetectors;
import com.tortugolen.echo_detectors.Recipes.DetectorCraftingRecipe;
import com.tortugolen.echo_detectors.Recipes.DetectorUpgradeRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EchoDetectors.MOD_ID);

    public static final RegistryObject<RecipeSerializer<DetectorCraftingRecipe>> DETECTOR_CRAFTING_RECIPE = SERIALIZERS.register(
            "detector_crafting", DetectorCraftingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<DetectorUpgradeRecipe>> DETECTOR_UPGRADE_RECIPE = SERIALIZERS.register(
            "detector_upgrade", DetectorUpgradeRecipe.Serializer::new);

}
