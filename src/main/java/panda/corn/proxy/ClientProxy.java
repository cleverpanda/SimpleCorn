package panda.corn.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import panda.corn.entity.MyEntityFireworkRocket;
import panda.corn.init.ModBlocks;
import panda.corn.init.ModItems;
import panda.corn.other.RenderFireworkEntity;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(MyEntityFireworkRocket.class, RenderFireworkEntity.INSTANCE);
		
		registerItemModel(ModItems.CHICKENCORNCHOWDER);
		registerItemModel(ModItems.CORNCHOWDER);
		registerItemModel(ModItems.CORNCOB);
		registerItemModel(ModItems.KERNELS);
		registerItemModel(ModItems.POPFIREWORK);
		registerItemModel(ModItems.POPPEDCORN);
		registerItemModel(ModItems.ROASTEDCORN);
		registerItemModel(Item.getItemFromBlock(ModBlocks.CORN));
	}

	public static void registerItemModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
