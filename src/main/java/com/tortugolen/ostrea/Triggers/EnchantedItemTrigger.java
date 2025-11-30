package com.tortugolen.ostrea.Triggers;

import com.google.gson.JsonObject;
import com.tortugolen.ostrea.Init.InitEnchantments;
import com.tortugolen.ostrea.Ostrea;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class EnchantedItemTrigger extends SimpleCriterionTrigger<EnchantedItemTrigger.Instance> {

    public static final ResourceLocation ID = new ResourceLocation(Ostrea.MOD_ID, "enchanted_item");
    public static final EnchantedItemTrigger INSTANCE = new EnchantedItemTrigger();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate pPlayer, DeserializationContext context) {

        ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("item"));
        EnchantmentPredicate enchantmentPredicate = EnchantmentPredicate.fromJson(json.get("enchantment"));

        return new Instance(pPlayer, itemPredicate, enchantmentPredicate);
    }

    public void trigger(ServerPlayer pPlayer, ItemStack pStack) {
        this.trigger(pPlayer, instance -> instance.matches(pStack));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        private final ItemPredicate item;
        private final EnchantmentPredicate enchantment;

        public Instance(ContextAwarePredicate pPlayer, ItemPredicate item, EnchantmentPredicate enchantment) {
            super(EnchantedItemTrigger.ID, pPlayer);
            this.item = item;
            this.enchantment = enchantment;
        }

        public boolean matches(ItemStack pStack) {
            return EnchantmentHelper.getItemEnchantmentLevel(InitEnchantments.SHELL_OPENER.get(), pStack) > 0;
        }
    }
}
