package panda.corn.registry;

import java.util.ArrayList;
import java.util.List;

import panda.corn.objects.BlockCorn;
import panda.corn.objects.ItemCornChowder;
import panda.corn.objects.ItemCornCob;
import panda.corn.objects.ItemKernels;
import panda.corn.objects.ItemPopcorn;
import panda.corn.objects.ItemRoastedCorn;
import panda.corn.objects.MyFireworkItem;
import panda.corn.other.Config;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public final class ObjectList {
	
	/*
	 * Declare items here and then subsequently add them to getList(); They will be automatically moved to registration and model loading.
	 */
	public static final Block CORN = new BlockCorn();
	
	public static final Item COB = new ItemCornCob(Config.cobFood, Config.cobSat);
	public static final Item KERNELS = new ItemKernels();
	public static final Item ROASTED_CORN = new ItemRoastedCorn(Config.roastedFood,Config.roastedSat);
	public static final Item POPCORN = new ItemPopcorn(Config.popcornFood,Config.popcornSat);
	public static final Item CHOWDER = new ItemCornChowder(Config.chowderFood,Config.chowderSat).setRegistryName("cornchowder");
	public static final Item CHICKEN_CHOWDER = new ItemCornChowder(Config.chicchowderFood,Config.chicchowderSat).setRegistryName("chickencornchowder");
	public static final Item POP_FIREWORK = new MyFireworkItem();
	
	
	public static List<Item> getItemList() {
		List<Item> list = new ArrayList<Item>();
		list.add(COB);
		list.add(KERNELS);
		list.add(ROASTED_CORN);
		list.add(POPCORN);
		list.add(CHOWDER);
		list.add(CHICKEN_CHOWDER);
		list.add(POP_FIREWORK);

		return list;
	}
	
	public static List<Block> getBlockList() {
		List<Block> list = new ArrayList<Block>();
		list.add(CORN);

		return list;
	}
}
