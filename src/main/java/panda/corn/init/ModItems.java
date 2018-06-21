package panda.corn.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import panda.corn.ConfigSimpleCorn;
import panda.corn.Corn;
import panda.corn.objects.MyFireworkItem;


@EventBusSubscriber
public final class ModItems {

	public static final Item COB = simply(new ItemFood(ConfigSimpleCorn.cobFood, ConfigSimpleCorn.cobSat,false),"corncob");
	public static final Item KERNELS = simply(new ItemSeeds(ModBlocks.CORN, Blocks.FARMLAND),"kernels");
	public static final Item ROASTED_CORN = simply(new ItemFood(ConfigSimpleCorn.roastedFood,ConfigSimpleCorn.roastedSat,false),"roastedcorn");
	public static final Item POPCORN = simply(new ItemFood(ConfigSimpleCorn.popcornFood,ConfigSimpleCorn.popcornSat,false){
		@Override public int getMaxItemUseDuration(ItemStack stack){return 8;}},"poppedcorn");
	public static final Item CHOWDER = simply(new ItemSoup(ConfigSimpleCorn.chowderFood),"cornchowder");
	public static final Item CHICKEN_CHOWDER = simply(new ItemSoup(ConfigSimpleCorn.chicchowderFood),"chickencornchowder");
	public static final Item POP_FIREWORK = simply(new MyFireworkItem(),"popfirework");

	private static Item simply(Item item, String name) { 
		return item.setRegistryName(Corn.MODID, name).setUnlocalizedName(Corn.MODID + "." + name);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		registry.register(COB);
		registry.register(KERNELS);
		registry.register(ROASTED_CORN);
		registry.register(POPCORN);
		registry.register(CHOWDER);
		registry.register(CHICKEN_CHOWDER);
		registry.register(POP_FIREWORK);

		registry.register(new ItemBlock( ModBlocks.CORN).setRegistryName( ModBlocks.CORN.getRegistryName()));
	}
	
}
