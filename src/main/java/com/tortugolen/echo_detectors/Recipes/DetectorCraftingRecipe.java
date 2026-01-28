package com.tortugolen.echo_detectors.Recipes;

import com.google.gson.JsonObject;
import com.tortugolen.echo_detectors.Init.InitRecipes;
import com.tortugolen.echo_detectors.Items.AbstractDetectorItem;
import com.tortugolen.echo_detectors.Items.EchoDetectorItem;
import com.tortugolen.echo_detectors.Items.ResonantDetectorItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class DetectorCraftingRecipe extends ShapedRecipe {

    public DetectorCraftingRecipe(ResourceLocation pId, String pGroup, CraftingBookCategory pCategory, int pWidth, int pHeight, NonNullList<Ingredient> pRecipeItems, ItemStack pResult) {
        super(pId, pGroup, pCategory, pWidth, pHeight, pRecipeItems, pResult);
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        ItemStack result = super.assemble(pContainer, pRegistryAccess);

        ItemStack baseDetector = ItemStack.EMPTY;
        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack stack = pContainer.getItem(i);
            if (stack.getItem() instanceof AbstractDetectorItem) {
                baseDetector = stack;
                break;
            }
        }

        if (!baseDetector.isEmpty()) {
            AbstractDetectorItem.copyUpgrades(baseDetector, result);

            ItemStack storedItem = ItemStack.EMPTY;

            if (baseDetector.getItem() instanceof EchoDetectorItem) {
                storedItem = EchoDetectorItem.getStoredItem(baseDetector);
            } else if (baseDetector.getItem() instanceof ResonantDetectorItem) {
                storedItem = ResonantDetectorItem.getStoredItem(baseDetector);
            }

            if (!storedItem.isEmpty()) {
                if (result.getItem() instanceof EchoDetectorItem) {
                    EchoDetectorItem.setStoredItem(result, storedItem);
                } else if (result.getItem() instanceof ResonantDetectorItem) {
                    ResonantDetectorItem.setStoredItem(result, storedItem);
                }
            }
        }

        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipes.DETECTOR_CRAFTING_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<DetectorCraftingRecipe> {

        @Override
        public DetectorCraftingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            ShapedRecipe tempRecipe = RecipeSerializer.SHAPED_RECIPE.fromJson(pRecipeId, pJson);

            return new DetectorCraftingRecipe(pRecipeId, tempRecipe.getGroup(), tempRecipe.category(), tempRecipe.getWidth(), tempRecipe.getHeight(), tempRecipe.getIngredients(), tempRecipe.getResultItem(null));
        }

        @Override
        public DetectorCraftingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            CraftingBookCategory category = pBuffer.readEnum(CraftingBookCategory.class);
            int width = pBuffer.readVarInt();
            int height = pBuffer.readVarInt();

            net.minecraft.core.NonNullList<Ingredient> ingredients =
                    net.minecraft.core.NonNullList.withSize(width * height, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                ingredients.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack result = pBuffer.readItem();

            return new DetectorCraftingRecipe(pRecipeId, group, category, width, height, ingredients, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, DetectorCraftingRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.getGroup());
            pBuffer.writeEnum(pRecipe.category());
            pBuffer.writeVarInt(pRecipe.getWidth());
            pBuffer.writeVarInt(pRecipe.getHeight());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.getResultItem(null));
        }
    }
}