package panda.corn.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import panda.corn.SimpleCorn;

@EventBusSubscriber(modid = SimpleCorn.MODID)
public class InsertTrades {
	
	@SubscribeEvent
	public static void addVillageTrades(final VillagerTradesEvent event) {
		System.out.println("adding Villager trades");
		if(event.getType() == VillagerProfession.FARMER) {
			System.out.println("adding farmer trades");
			List<ITrade> trades = new ArrayList<>();		
			trades.add(new BasicTrade(new ItemStack(ModItems.CORNCOB, 16),new ItemStack(Items.EMERALD, 1), 12, 10, 0.05F));
			//(ItemStack price, ItemStack forSale, int maxTrades, int xp, float priceMult)
			event.getTrades().put(2,trades);
		}
	}
	
	@SubscribeEvent
	public static void addWandererTrades(final WandererTradesEvent event) {
		System.out.println("adding wanderer trades");
		event.getGenericTrades().add(new BasicTrade(1, new ItemStack(ModItems.KERNELS, 1), 12, 5, 1F));
		//(ItemStack price, ItemStack price2, ItemStack forSale, int maxTrades, int xp, float priceMult)
	}
}
