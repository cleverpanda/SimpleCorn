package panda.corn.init;

import java.util.List;

import com.google.common.base.Throwables;

import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import panda.corn.SimpleCorn;

//Majority of code modified from Vazkii
//Copyright 2021 cc-by-nc-sa-4.0
//https://github.com/Vazkii/Quark/blob/bb564ebabed12c1c2090792e1f1e43796f9c7266/src/main/java/vazkii/quark/base/handler/MiscUtil.java

@EventBusSubscriber(modid = SimpleCorn.MODID)
public class InsertChestLoot {
	
	private static final MethodHandle LOOT_TABLE_POOLS, LOOT_POOL_ENTRIES;

	static {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		Field lootTablePools = ObfuscationReflectionHelper.findField(LootTable.class, "field_186466_c");
		Field lootPoolEntries = ObfuscationReflectionHelper.findField(LootPool.class, "field_186453_a");
		try {
			LOOT_TABLE_POOLS = lookup.unreflectGetter(lootTablePools);
			LOOT_POOL_ENTRIES = lookup.unreflectGetter(lootPoolEntries);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
    
	@SubscribeEvent
	public static void onLootTableLoaded(final LootTableLoadEvent event) {
		System.out.println("onlootloaded");
		int weight = 0;
		ResourceLocation res = event.getName();
		if(res.equals(LootTables.CHESTS_ABANDONED_MINESHAFT))
			weight = 10;
		else if(res.equals(LootTables.CHESTS_SIMPLE_DUNGEON))
			weight = 10;

		if(weight > 0) {
			LootEntry entry = ItemLootEntry.builder(ModItems.KERNELS).quality(0).weight(weight).acceptFunction(SetCount.builder(new RandomValueRange(1, 3))).build();
			addToLootTable(event.getTable(), entry);
		}
	}
	
	public static void addToLootTable(LootTable table, LootEntry entry) {
		List<LootPool> pools = getPools(table);
		if (!pools.isEmpty()) {
			getEntries(pools.get(0)).add(entry);
		}
	}
	
	public static List<LootPool> getPools(LootTable table) {
		try {
			return (List<LootPool>) LOOT_TABLE_POOLS.invokeExact(table);
		} catch (Throwable throwable) {
			Throwables.throwIfUnchecked(throwable);
			throw new RuntimeException(throwable);
		}
	}

	public static List<LootEntry> getEntries(LootPool pool) {
		try {
			return (List<LootEntry>) LOOT_POOL_ENTRIES.invokeExact(pool);
		} catch (Throwable throwable) {
			Throwables.throwIfUnchecked(throwable);
			throw new RuntimeException(throwable);
		}
	}
}
