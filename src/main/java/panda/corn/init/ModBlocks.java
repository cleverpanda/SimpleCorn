package panda.corn.init;

import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import panda.corn.ConfigSimpleCorn;
import panda.corn.Corn;
import panda.corn.objects.BlockCorn;
import panda.corn.other.MyRecipeFireworks;

@EventBusSubscriber
public final class ModBlocks {

	public static final Block CORN = simply(new BlockCorn(),"corn");
	
	public static Block simply(Block block, String name) {
		return block.setRegistryName(Corn.MODID, name).setTranslationKey(Corn.MODID + "." + name);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(CORN);	
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		if(ConfigSimpleCorn.PopcornFireworks){
			event.getRegistry().register(new MyRecipeFireworks().setRegistryName(new ResourceLocation(Corn.MODID+":fireworks")));
		}
	}
}
