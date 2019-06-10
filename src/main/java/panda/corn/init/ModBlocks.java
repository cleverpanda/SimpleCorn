package panda.corn.init;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import panda.corn.SimpleCorn;
import panda.corn.blocks.BlockCorn;
import panda.corn.blocks.BlockCornMid;
import panda.corn.blocks.BlockCornTop;

@EventBusSubscriber(modid = SimpleCorn.MODID, bus = EventBusSubscriber.Bus.MOD)
@ObjectHolder(SimpleCorn.MODID)
public final class ModBlocks {

	public static final BlockCorn CORN = null;
	public static final BlockCornMid CORN_MID = null;
	public static final BlockCornTop CORN_TOP = null;
	
	public static Block simply(Block block, String name) {
		return block.setRegistryName(SimpleCorn.MODID, name);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
			simply(new BlockCorn(),"corn"),
			simply(new BlockCornMid(), "corn_mid"),
			simply(new BlockCornTop(), "corn_top"));	
	}

}
