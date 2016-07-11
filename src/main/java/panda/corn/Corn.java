package panda.corn;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Corn.MODID, name = Corn.NAME, version = Corn.VERSION)

public class Corn {
	
		public static final String MODID = "corn";
		public static final String NAME = "Corn";
		public static final String VERSION = "1.1";
		public static final Block  corn = new BlockCorn();
		public static final Item kernels = new ItemKernels();
	    @Instance(MODID)
	    public static Corn instance;
	    
	    @EventHandler
	    public void preInit(FMLPreInitializationEvent event)
	    {
	    	corn.setUnlocalizedName("corn");
	        GameRegistry.registerBlock(corn, "corn");
	        
	        Item itemCob = (Item)(new ItemCornCob(2, 3.6F,false).setUnlocalizedName("corncob"));
	        GameRegistry.registerItem(itemCob, "corncob");
	        
	        Item itemRoasted = (Item)(new ItemRoastedCorn(8, 10F,false).setUnlocalizedName("roastedcorn"));
	        GameRegistry.registerItem(itemRoasted, "roastedcorn");
	        
	        Item chowder = (Item)(new ItemCornChowder(6,7.2F).setUnlocalizedName("cornchowder"));
	        GameRegistry.registerItem(chowder, "cornchowder");
	        
	        Item chickenchowder = (Item)(new ItemCornChowder(10,9.6F).setUnlocalizedName("chickencornchowder"));
	        GameRegistry.registerItem(chickenchowder, "chickencornchowder");
	        
	        kernels.setUnlocalizedName("kernels");
	        GameRegistry.registerItem(kernels, "kernels");
	        
	        MapGenStructureIO.registerStructureComponent(ComponentCornField.class, "Vicf");
	        VillagerRegistry.instance().registerVillageCreationHandler(new CornWorldGen());
	        
	        //Item popcorn = (Item)(new ItemPopcorn(1,.2F).setUnlocalizedName("poppedcorn"));
	        //GameRegistry.registerItem(popcorn, "poppedcorn");
	        
	        GameRegistry.addShapelessRecipe(new ItemStack(chowder),
	                new Object[] {
	                        new ItemStack(Items.bowl, 1),
	                        new ItemStack(Items.milk_bucket, 1),
	                        new ItemStack(kernels, 1),
	                        new ItemStack(kernels, 1)
	        });
	        
	        GameRegistry.addShapelessRecipe(new ItemStack(chickenchowder),
	                new Object[] {
	                        new ItemStack(Items.bowl, 1),
	                        new ItemStack(Items.milk_bucket, 1),
	                        new ItemStack(Items.cooked_chicken, 1),
	                        new ItemStack(kernels, 1),
	                        new ItemStack(kernels, 1)
	                        
	        });
	        
	        GameRegistry.addShapelessRecipe(new ItemStack(chickenchowder),
	                new Object[] {
	                        new ItemStack(chowder, 1),
	                        new ItemStack(Items.cooked_chicken, 1)
	        });
	        
	        GameRegistry.addShapelessRecipe(new ItemStack(kernels,2),
	                new Object[] {
	                        new ItemStack(itemCob)
	        });

	        
	        GameRegistry.addSmelting(itemCob, new ItemStack(itemRoasted), .2F);
	        //GameRegistry.addSmelting(kernels, new ItemStack(popcorn), .01F);
	        MinecraftForge.addGrassSeed(new ItemStack(kernels), 8);
	        

	        
	    }
	    
	    @EventHandler
	    public void Init(FMLInitializationEvent event)
	    {
	    	if(event.getSide() == Side.CLIENT)
	    	{
	    		Item corn = GameRegistry.findItem(MODID, "corn");
	    		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("corn:corn", "inventory");
	    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(corn, 0, itemModelResourceLocation);
	    		
	    		Item ItemKernels = GameRegistry.findItem(MODID, "kernels");
	    		itemModelResourceLocation = new ModelResourceLocation("corn:kernels", "inventory");
	    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ItemKernels, 0, itemModelResourceLocation);
	    		
	    		Item ItemCornCob = GameRegistry.findItem(MODID, "corncob");
	    		itemModelResourceLocation = new ModelResourceLocation("corn:corncob", "inventory");
	    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ItemCornCob, 0, itemModelResourceLocation);
	    		
	    		Item ItemRoastCorn = GameRegistry.findItem(MODID, "roastedcorn");
	    		itemModelResourceLocation = new ModelResourceLocation("corn:roastedcorn", "inventory");
	    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ItemRoastCorn, 0, itemModelResourceLocation);
	    		
	    		Item Cornchowder = GameRegistry.findItem(MODID, "cornchowder");
	    		itemModelResourceLocation = new ModelResourceLocation("corn:cornchowder", "inventory");
	    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Cornchowder, 0, itemModelResourceLocation);
	    		
	    		Item chickenCornchowder = GameRegistry.findItem(MODID, "chickencornchowder");
	    		itemModelResourceLocation = new ModelResourceLocation("corn:chickencornchowder", "inventory");
	    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(chickenCornchowder, 0, itemModelResourceLocation);

	    	}
	    }
}
