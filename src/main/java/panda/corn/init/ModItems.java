package panda.corn.init;

import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import panda.corn.ConfigSimpleCorn;
import panda.corn.SimpleCorn;
import panda.corn.items.ItemKernels;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = SimpleCorn.MODID, bus = EventBusSubscriber.Bus.MOD)
@ObjectHolder(SimpleCorn.MODID)
public final class ModItems {

	public static final Item CORNCOB = null;
	public static final Item KERNELS = null;
	public static final Item ROASTEDCORN = null;
	public static final Item POPPEDCORN = null;
	public static final Item CORNCHOWDER = null;
	public static final Item CHICKENCORNCHOWDER = null;

	private static Item simply(Item item, String name) {
		return item.setRegistryName(SimpleCorn.MODID, name);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(simply(makeFood(1, 0.3f), "corncob"),
			simply(new ItemKernels(), "kernels"),
		    simply(makeFood(6, 0.6f), "roastedcorn"),
		    simply(new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(1).saturation(0.1f).fastToEat().setAlwaysEdible().build())), "poppedcorn"),
		    simply(makeStew(7), "cornchowder"),
		    simply(makeStew(10), "chickencornchowder"),
		    new BlockItem(ModBlocks.CORN, new Item.Properties().maxStackSize(64)).setRegistryName(SimpleCorn.MODID, "corn")
		);
		
		//OreDictionary.registerOre("seedsCorn", ModItems.KERNELS);
		//OreDictionary.registerOre("seedCorn", ModItems.KERNELS);
		//OreDictionary.registerOre("listAllVeggies", ModItems.CORNCOB);
		//OreDictionary.registerOre("cropCorn", ModItems.CORNCOB);
		//OreDictionary.registerOre("listAllVeggies", ModItems.ROASTEDCORN);
		//OreDictionary.registerOre("listAllseed", ModItems.KERNELS);
		
		//CookingRecipeBuilder.func_218629_c(Ingredient.fromItems(ModItems.CORNCOB), ModItems.ROASTEDCORN, 0.20F, 200);
		//CookingRecipeBuilder.func_218629_c(Ingredient.fromItems(ModItems.KERNELS), ModItems.POPPEDCORN, 0.01F, 200);
	}
	
	
	public static Item makeStew(int hunger) {
		return new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD)
				.food((new Food.Builder()).hunger(hunger).saturation(0.6F).build()));
	}
	
	public static Item makeFood(int hunger,float sat) {
		return new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(hunger).saturation(sat).build()));		
	}

}
