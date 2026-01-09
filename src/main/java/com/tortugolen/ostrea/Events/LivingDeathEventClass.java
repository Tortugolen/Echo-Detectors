package com.tortugolen.ostrea.Events;

import com.tortugolen.ostrea.Items.ReceptaclePearlItem;
import com.tortugolen.ostrea.Ostrea;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ostrea.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingDeathEventClass {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent pEvent) {
        if (pEvent.getEntity().level().isClientSide()) return;

        Entity pAttacker = pEvent.getSource().getEntity();
        LivingEntity pTarget = pEvent.getEntity();

        if (!(pAttacker instanceof LivingEntity attacker)) return;

        ItemStack pStack = attacker.getOffhandItem();

        if (!(pStack.getItem() instanceof ReceptaclePearlItem receptaclePearlItem)) return;

        MobEffect pEffect = ReceptaclePearlItem.ENTITY_EFFECTS.get(pTarget.getType());

        if (pEffect != null) {
            receptaclePearlItem.storeEffect(pStack, pEffect, attacker);
        }
    }
}
