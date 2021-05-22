package panda.corn.init;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import panda.corn.SimpleCorn;
import panda.corn.items.ItemKernels;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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

	private static Item simply(@Nonnull Item item, @Nonnull String name) {
		Preconditions.checkNotNull(item, "Entry cannot be null!");
		Preconditions.checkNotNull(name, "Registry name to assign to entry cannot be null!");
		return item.setRegistryName(SimpleCorn.MODID, name);
	}

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(simply(makeFood(1, 0.3f), "corncob"),
			simply(new ItemKernels(), "kernels"),
		    simply(makeFood(6, 0.6f), "roastedcorn"),
		    simply(new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(1).saturation(0.1f).fastToEat().setAlwaysEdible().build())), "poppedcorn"),
		    simply(makeStew(7), "cornchowder"),
		    simply(makeStew(10), "chickencornchowder"),
		    new BlockItem(ModBlocks.CORN, new Item.Properties().maxStackSize(64)).setRegistryName(SimpleCorn.MODID, "corn")
		);
	}
	
	
	public static Item makeStew(int hunger) {
		return new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD)
				.food((new Food.Builder()).hunger(hunger).saturation(0.6F).build()));
	}
	
	public static Item makeFood(int hunger, float sat) {
		return new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(hunger).saturation(sat).build()));		
	}
	
}
