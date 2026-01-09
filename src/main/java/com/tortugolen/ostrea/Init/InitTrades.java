package com.tortugolen.ostrea.Init;

import com.tortugolen.ostrea.Ostrea;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Ostrea.MOD_ID)
public class InitTrades {
    @SubscribeEvent
    public static void addTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.WEAPONSMITH || event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(InitItems.GEM_POLISHING_SMITHING_TEMPLATE.get(), 1),
                    4, 10, 0f));
        }
    }
}