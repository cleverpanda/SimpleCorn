package panda.corn.init;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

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
	
	public static Block simply(@Nonnull Block block, @Nonnull String name) {
		Preconditions.checkNotNull(block, "Entry cannot be null!");
		Preconditions.checkNotNull(name, "Registry name to assign to entry cannot be null!");
		return block.setRegistryName(SimpleCorn.MODID, name);
	}

	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
			simply(new BlockCorn()   , "corn"),
			simply(new BlockCornMid(), "corn_mid"),
			simply(new BlockCornTop(), "corn_top")
		);	
	}

}
