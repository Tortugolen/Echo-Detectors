package com.tortugolen.echo_detectors.Recipes;

import com.google.gson.JsonObject;
import com.tortugolen.echo_detectors.Init.InitItems;
import com.tortugolen.echo_detectors.Init.InitRecipes;
import com.tortugolen.echo_detectors.Items.AbstractDetectorItem;
import com.tortugolen.echo_detectors.Items.EchoDetectorItem;
import com.tortugolen.echo_detectors.Items.ResonantDetectorItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

public class DetectorUpgradeRecipe extends SmithingTransformRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient upgrade;
    private final ItemStack result;

    public DetectorUpgradeRecipe(ResourceLocation pId, Ingredient pTemplate, Ingredient pBase, Ingredient pupgrade, ItemStack pResult) {
        super(pId, pTemplate, pBase, pupgrade, pResult);
        this.template = pTemplate;
        this.base = pBase;
        this.upgrade = pupgrade;
        this.result = pResult;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        ItemStack detectorItem = pContainer.getItem(1);
        ItemStack upgradeItem = pContainer.getItem(2);

        if (detectorItem.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = detectorItem.copy();

        AbstractDetectorItem.copyUpgrades(detectorItem, result);

        if (detectorItem.getItem() instanceof EchoDetectorItem) {
            ItemStack storedItem = EchoDetectorItem.getStoredItem(detectorItem);
            if (!storedItem.isEmpty()) {
                EchoDetectorItem.setStoredItem(result, storedItem);
            }
        }

        if (detectorItem.getItem() instanceof ResonantDetectorItem) {
            ItemStack storedItem = ResonantDetectorItem.getStoredItem(detectorItem);
            if (!storedItem.isEmpty()) {
                ResonantDetectorItem.setStoredItem(result, storedItem);
            }
        }

        if (upgradeItem.is(InitItems.LOCATION_UPGRADE.get())) {
            AbstractDetectorItem.applyLocationUpgrade(result);
        } else if (upgradeItem.is(InitItems.NAME_UPGRADE.get())) {
            AbstractDetectorItem.applyNameUpgrade(result);
        }

        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipes.DETECTOR_UPGRADE_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<DetectorUpgradeRecipe> {
        @Override
        public DetectorUpgradeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient template = Ingredient.fromJson(pJson.get("template"));
            Ingredient base = Ingredient.fromJson(pJson.get("base"));
            Ingredient upgrade = Ingredient.fromJson(pJson.get("upgrade"));
            ItemStack result = ItemStack.EMPTY;

            return new DetectorUpgradeRecipe(pRecipeId, template, base, upgrade, result);
        }

        @Override
        public DetectorUpgradeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient template = Ingredient.fromNetwork(pBuffer);
            Ingredient base = Ingredient.fromNetwork(pBuffer);
            Ingredient upgrade = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();

            return new DetectorUpgradeRecipe(pRecipeId, template, base, upgrade, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, DetectorUpgradeRecipe pRecipe) {
            pRecipe.template.toNetwork(pBuffer);
            pRecipe.base.toNetwork(pBuffer);
            pRecipe.upgrade.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
        }
    }
}