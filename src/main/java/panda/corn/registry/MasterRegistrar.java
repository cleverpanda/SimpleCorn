package panda.corn.registry;

import java.util.Iterator;
import java.util.List;

import panda.corn.Corn;
import panda.corn.entity.MyEntityFireworkRocket;
import panda.corn.gen.ComponentCornField;
import panda.corn.gen.CornWorldGen;
import panda.corn.other.Config;
import panda.corn.other.MyRecipeFireworks;
import panda.corn.other.RenderFireworkEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;

public final class MasterRegistrar {
	private MasterRegistrar() {}
	
	public static void register(FMLPreInitializationEvent e, List<?> list) {
		Iterator<?> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object k = iterator.next();
			if (k instanceof Block) {
				Block block = (Block) k;
				ForgeRegistries.BLOCKS.register(block);
				block.setUnlocalizedName(Corn.MODID + "." + block.getRegistryName().getResourcePath());
				if (Item.getItemFromBlock(block) == null)
					ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			} else if (k instanceof Item) {
				ForgeRegistries.ITEMS.register((Item) k);
				((Item) k).setUnlocalizedName(Corn.MODID + "." + ((Item) k).getRegistryName().getResourcePath());
			}

		}
		if (e.getSide() == Side.CLIENT)
			registerModels(list);
	}

	public static void registerModels(List<?> list) {
		Iterator<?> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object k = iterator.next();
			Item item = null;
			if (k instanceof Block) {
				item = Item.getItemFromBlock((Block) k);
			} else if (k instanceof Item) {
				item = (Item) k;
			}

			if (item != null ) {
				ModelLoader.setCustomModelResourceLocation(item, 0,new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		}

	}

	public static void callRegistry(FMLPreInitializationEvent e) {
		register(e, ObjectList.getItemList());
		register(e, ObjectList.getBlockList());
		GameRegistry.addSmelting(ObjectList.COB, new ItemStack(ObjectList.ROASTED_CORN), .2F);
		GameRegistry.addSmelting(ObjectList.KERNELS, new ItemStack(ObjectList.POPCORN,2), .01F);
		MinecraftForge.addGrassSeed(new ItemStack(ObjectList.KERNELS), Config.kernelWeight);
		if(e.getSide() == Side.CLIENT){
				RenderingRegistry.registerEntityRenderingHandler(MyEntityFireworkRocket.class, RenderFireworkEntity.INSTANCE);
		}
		EntityRegistry.registerModEntity(new ResourceLocation("simplecorn:entitypopfirework"),MyEntityFireworkRocket.class, "entitypopfirework", 132, Corn.instance, 64, 3, true);
		
		MapGenStructureIO.registerStructureComponent(ComponentCornField.class, "Vicf");
		VillagerRegistry.instance().registerVillageCreationHandler(new CornWorldGen());
		ForgeRegistries.RECIPES.register(new MyRecipeFireworks().setRegistryName(new ResourceLocation(Corn.MODID+":fireworks")));
	}
}
