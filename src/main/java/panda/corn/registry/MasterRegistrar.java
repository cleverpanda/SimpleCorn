package panda.corn.registry;

import java.util.Iterator;
import java.util.List;

import panda.corn.Corn;
import panda.corn.entity.MyEntityFireworkRocket;
import panda.corn.gen.ComponentCornField;
import panda.corn.gen.CornWorldGen;
import panda.corn.other.MyRecipeFireworks;
import panda.corn.other.Recipes;
import panda.corn.other.RenderFireworkEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

public final class MasterRegistrar {
	
	public static void register(FMLPreInitializationEvent e, List<?> list) {
		Iterator<?> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object k = iterator.next();
			if (k instanceof Block) {
				Block block = (Block) k;
				GameRegistry.register(block);
				block.setUnlocalizedName(Corn.MODID + "." + block.getRegistryName().getResourcePath());
				if (Item.getItemFromBlock(block) == null)
					GameRegistry.register(new ItemBlock(block), block.getRegistryName());
			} else if (k instanceof Item) {
				GameRegistry.register((Item) k);
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
		Recipes.register();
		if(e.getSide() == Side.CLIENT){
				RenderingRegistry.registerEntityRenderingHandler(MyEntityFireworkRocket.class, RenderFireworkEntity.INSTANCE);
		}
		EntityRegistry.registerModEntity(new ResourceLocation("simplecorn:entitypopfirework"),MyEntityFireworkRocket.class, "entitypopfirework", 132, Corn.instance, 64, 3, true);
		
		MapGenStructureIO.registerStructureComponent(ComponentCornField.class, "Vicf");
		VillagerRegistry.instance().registerVillageCreationHandler(new CornWorldGen());
		RecipeSorter.register("simplecorn:fireworks", MyRecipeFireworks.class, Category.SHAPELESS, "after:minecraft:fireworks");
	}
}
