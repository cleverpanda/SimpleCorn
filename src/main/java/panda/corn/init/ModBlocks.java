package panda.corn.init;

import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import panda.corn.ConfigSimpleCorn;
import panda.corn.SimpleCorn;
import panda.corn.blocks.BlockCorn;
import panda.corn.blocks.BlockCornMid;
import panda.corn.blocks.BlockCornTop;
import panda.corn.other.MyRecipeFireworks;

@EventBusSubscriber(modid = SimpleCorn.MODID)
@ObjectHolder(SimpleCorn.MODID)
public final class ModBlocks {
	
	private ModBlocks() {
	    throw new IllegalStateException("Utility class");
	}

	public static final BlockCorn CORN = null;
	public static final BlockCornMid CORN_MID = null;
	public static final BlockCornTop CORN_TOP = null;
	
	public static Block simply(Block block, String name) {
		return block.setRegistryName(SimpleCorn.MODID, name).setTranslationKey(SimpleCorn.MODID + "." + name);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
				simply(new BlockCorn(),"corn"), 
				simply(new BlockCornMid(), "corn_mid"), 
				simply(new BlockCornTop(), "corn_top"));	
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		if(ConfigSimpleCorn.popcornFireworks){
			event.getRegistry().register(new MyRecipeFireworks().setRegistryName(new ResourceLocation(SimpleCorn.MODID+":fireworks")));
		}
	}
}
