package com.tortugolen.ostrea.Triggers;

import com.google.gson.JsonObject;
import com.tortugolen.ostrea.Ostrea;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRemovedTrigger extends SimpleCriterionTrigger<EffectRemovedTrigger.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(Ostrea.MOD_ID, "effect_removed");
    public static final EffectRemovedTrigger INSTANCE = new EffectRemovedTrigger();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pContext) {

        ItemPredicate item = ItemPredicate.fromJson(pJson.get("item"));

        boolean allEffects = false;
        if (pJson.has("all_effects")) {
            allEffects = pJson.get("all_effects").getAsBoolean();
        }

        MobEffect effect = null;
        if (!allEffects && pJson.has("effect")) {
            ResourceLocation effectId = new ResourceLocation(pJson.get("effect").getAsString());
            effect = ForgeRegistries.MOB_EFFECTS.getValue(effectId);
        }

        return new Instance(pPredicate, item, allEffects, effect);
    }

    public void trigger(ServerPlayer pPlayer, ItemStack pStack, MobEffect removedEffect) {
        this.trigger(pPlayer, instance -> instance.matches(pStack, removedEffect));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        private final boolean allEffects;
        private final MobEffect effect;
        private final ItemPredicate item;

        public Instance(ContextAwarePredicate pPredicate, ItemPredicate pItem, boolean allEffects, MobEffect effect) {
            super(EffectRemovedTrigger.ID, pPredicate);
            this.allEffects = allEffects;
            this.effect = effect;
            this.item = pItem;
        }

        public boolean matches(ItemStack pStack, MobEffect pEffect) {

            if (!this.item.matches(pStack))
                return false;

            if (this.allEffects)
                return true;

            if (this.effect == null)
                return false;

            return this.effect == pEffect;
        }
    }
}
