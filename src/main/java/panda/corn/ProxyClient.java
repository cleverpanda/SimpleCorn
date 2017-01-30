package panda.corn;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProxyClient extends CommonProxy{

	
	
	public void init(FMLInitializationEvent e) {
		super.init(e);
		Item corn = GameRegistry.findItem(Corn.MODID, "corn");
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("corn:corn", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(corn, 0, itemModelResourceLocation);
		
		Item firework = GameRegistry.findItem(Corn.MODID, "popfirework");
		itemModelResourceLocation = new ModelResourceLocation("corn:popfirework", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(firework, 0, itemModelResourceLocation);

		Item ItemKernels = GameRegistry.findItem(Corn.MODID, "kernels");
		itemModelResourceLocation = new ModelResourceLocation("corn:kernels", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ItemKernels, 0, itemModelResourceLocation);

		Item ItemCornCob = GameRegistry.findItem(Corn.MODID, "corncob");
		itemModelResourceLocation = new ModelResourceLocation("corn:corncob", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ItemCornCob, 0, itemModelResourceLocation);

		Item ItemRoastCorn = GameRegistry.findItem(Corn.MODID, "roastedcorn");
		itemModelResourceLocation = new ModelResourceLocation("corn:roastedcorn", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ItemRoastCorn, 0, itemModelResourceLocation);

		Item Cornchowder = GameRegistry.findItem(Corn.MODID, "cornchowder");
		itemModelResourceLocation = new ModelResourceLocation("corn:cornchowder", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Cornchowder, 0, itemModelResourceLocation);

		Item chickenCornchowder = GameRegistry.findItem(Corn.MODID, "chickencornchowder");
		itemModelResourceLocation = new ModelResourceLocation("corn:chickencornchowder", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(chickenCornchowder, 0, itemModelResourceLocation);
		
		Item popcorn = GameRegistry.findItem(Corn.MODID, "poppedcorn");
		itemModelResourceLocation = new ModelResourceLocation("corn:poppedcorn", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(popcorn, 0, itemModelResourceLocation);
		RenderingRegistry.registerEntityRenderingHandler(MyEntityFireworkRocket.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), Items.FIREWORKS, Minecraft.getMinecraft().getRenderItem()));
	}
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }


}
