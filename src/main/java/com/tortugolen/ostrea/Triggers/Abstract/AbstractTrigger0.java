package com.tortugolen.ostrea.Triggers.Abstract;

import com.google.gson.JsonObject;
import com.tortugolen.ostrea.Ostrea;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class AbstractTrigger0 extends SimpleCriterionTrigger<AbstractTrigger0.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(Ostrea.MOD_ID, "abstract0");
    public static final AbstractTrigger0 INSTANCE = new AbstractTrigger0();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pContext) {
        return new Instance(pPredicate);
    }

    public void trigger(ServerPlayer pPlayer) {
        this.trigger(pPlayer, instance -> true);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(ContextAwarePredicate pPredicate) {
            super(AbstractTrigger0.ID, pPredicate);
        }
    }
}
