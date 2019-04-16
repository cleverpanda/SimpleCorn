package panda.corn.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import panda.corn.ConfigSimpleCorn;
import panda.corn.Corn;
import panda.corn.objects.ItemKernels;
import panda.corn.objects.MyFireworkItem;

@EventBusSubscriber(modid = Corn.MODID)
@ObjectHolder(Corn.MODID)
public final class ModItems {

	public static final Item CORNCOB = null;
	public static final Item KERNELS = null;
	public static final Item ROASTEDCORN = null;
	public static final Item POPPEDCORN = null;
	public static final Item CORNCHOWDER = null;
	public static final Item CHICKENCORNCHOWDER = null;
	public static final Item POPFIREWORK = null;

	private static Item simply(Item item, String name) {
		return item.setRegistryName(Corn.MODID, name).setTranslationKey(Corn.MODID + "." + name);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(simply(new ItemFood(ConfigSimpleCorn.cobFood, ConfigSimpleCorn.cobSat, false), "corncob"));
		registry.register(simply(new ItemKernels(), "kernels"));
		registry.register(simply(new ItemFood(ConfigSimpleCorn.roastedFood, ConfigSimpleCorn.roastedSat, false), "roastedcorn"));
		registry.register(simply(new ItemFood(ConfigSimpleCorn.popcornFood, ConfigSimpleCorn.popcornSat, false) {
			@Override
			public int getMaxItemUseDuration(ItemStack stack) {
				return 8;
			}
		}, "poppedcorn"));
		registry.register(simply(new ItemSoup(ConfigSimpleCorn.chowderFood), "cornchowder"));
		registry.register(simply(new ItemSoup(ConfigSimpleCorn.chicchowderFood), "chickencornchowder"));
		registry.register(simply(new MyFireworkItem(), "popfirework"));

		registry.register(new ItemBlock(ModBlocks.CORN).setRegistryName(ModBlocks.CORN.getRegistryName()));
	}

	@SubscribeEvent
	public static void recipes(RegistryEvent.Register<IRecipe> event) {
		OreDictionary.registerOre("seedsCorn", ModItems.KERNELS);
		OreDictionary.registerOre("seedCorn", ModItems.KERNELS);
		OreDictionary.registerOre("listAllVeggies", ModItems.CORNCOB);
		OreDictionary.registerOre("cropCorn", ModItems.CORNCOB);
		OreDictionary.registerOre("listAllVeggies", ModItems.ROASTEDCORN);
		OreDictionary.registerOre("listAllseed", ModItems.KERNELS);

		GameRegistry.addSmelting(ModItems.CORNCOB, new ItemStack(ModItems.ROASTEDCORN), .2F);
		GameRegistry.addSmelting(ModItems.KERNELS, new ItemStack(ModItems.POPPEDCORN, 2), .01F);
	}

}
