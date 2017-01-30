package panda.corn;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeFireworks;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Corn.MODID, name = Corn.NAME, version = Corn.VERSION)

public class Corn {

	public static final String MODID = "corn"; //Bad practice buuut...fuck it
	public static final String NAME = "Corn";
	public static final String VERSION = "1.3.2";
	public static final Block  corn = new BlockCorn();
	public static final Item kernels = new ItemKernels();
	public static final Item firework = new MyFireworkItem();
	public static final Entity fireworkentity = new MyEntityFireworkRocket(null);
	
	public static Logger log;
	
	public static int cobFood;
	public static float cobSat;
	public static int roastedFood;
	public static float roastedSat;
	public static int chowderFood;
	public static float chowderSat;
	public static int chicchowderFood;
	public static float chicchowderSat;
	public static int popcornFood;
	public static float popcornSat;
	public static int kernelWeight;
	public static int generationWeight;
	public static int growChance;
	
	@SidedProxy(serverSide = "panda.corn.ProxyServer", clientSide = "panda.corn.ProxyClient")
	public static CommonProxy proxy;
	public static ProxyClient PROXY = null;
	@Instance(MODID)
	public static Corn instance;
	
	
	
		
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		log = LogManager.getLogger(this.NAME);
		
		config.load();
		cobFood = config.getInt("VALUE_FOOD_COB", config.CATEGORY_GENERAL, 1, 1, 20,"Hunger value of corn cobs");
		cobSat = config.getFloat("VALUE_SATURATION_COB",  config.CATEGORY_GENERAL, 0.3F, 0F, 1F, "Saturation value of corn cobs");
		
		roastedFood = config.getInt("VALUE_FOOD_ROASTED_CORN", config.CATEGORY_GENERAL, 6, 1, 20,"Hunger value of roasted corn");
		roastedSat = config.getFloat("VALUE_SATURATION_ROASTED_CORN",  config.CATEGORY_GENERAL, 0.6F, 0F, 1F, "Saturation value of roasted corn");
		
		chowderFood = config.getInt("VALUE_FOOD_CHOWDER", config.CATEGORY_GENERAL, 7, 1, 20,"Hunger value of corn chowder");
		chowderSat= config.getFloat("VALUE_SATURATION_CHOWDER",  config.CATEGORY_GENERAL, 0.8F, 0F, 1F, "Saturation value of corn chowder");
		
		chicchowderFood = config.getInt("VALUE_FOOD_CHICKEN_CHOWDER", config.CATEGORY_GENERAL, 10, 1, 20,"Hunger value of chicken corn chowder");
		chicchowderSat = config.getFloat("VALUE_SATURATION_CHICKEN_CHOWDER",  config.CATEGORY_GENERAL, 0.8F, 0F, 1F, "Saturation value of chicken corn chowder");
		
		popcornFood = config.getInt("VALUE_FOOD_POPCORN", config.CATEGORY_GENERAL, 1, 1, 20,"Hunger value of popcorn");
		popcornSat = config.getFloat("VALUE_SATURATION_POPCORN",  config.CATEGORY_GENERAL, 0.1F, 0F, 1F, "Saturation value of popcorn");
		
		kernelWeight =  config.getInt("VALUE_KERNEL_DROP", config.CATEGORY_GENERAL, 8, 1, 100,"The relative chance of dropping kernels from grass. Seeds are 10");
		generationWeight =  config.getInt("VALUE_CORN_FIELD_GENERATION", config.CATEGORY_GENERAL, 45, 0, 100,"The relative chance of spawning corn fields. The small houses are 3, Blacksmiths are 15");
		growChance = config.getInt("VALUE_GROWTH_CHANCE", config.CATEGORY_GENERAL, 4, 0, 1000,"Chance of growing corn from rand.nextInt(n) == 0");
		    config.save();
		    
		    
		    IRecipe fireworkRecipe = null;
	        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
	        for (IRecipe recipe : recipes){
	            if (recipe.getClass().equals(RecipeFireworks.class)){
	                fireworkRecipe = recipe;
	                break;
	            }
	        }
	        if (fireworkRecipe != null){
	            recipes.remove(fireworkRecipe);
	            recipes.add(new MyRecipeFireworks());
	        } else {
	            log.error("Something in Recipes Borked.");
	        }
		

		corn.setUnlocalizedName("corn");
		corn.setRegistryName("corn");
		GameRegistry.register(corn);
		
		Item itemCob = (new ItemCornCob(cobFood, cobSat,false).setUnlocalizedName("corncob"));
		itemCob.setRegistryName("corncob");
		GameRegistry.register(itemCob);
		
		Item itemRoasted = (new ItemRoastedCorn(roastedFood, roastedSat,false).setUnlocalizedName("roastedcorn"));
		itemRoasted.setRegistryName("roastedcorn");
		GameRegistry.register(itemRoasted);
		
		Item chowder = (new ItemCornChowder(chowderFood,chowderSat).setUnlocalizedName("cornchowder"));
		chowder.setRegistryName("cornchowder");
		GameRegistry.register(chowder);
		
		Item chickenchowder = (new ItemCornChowder(chicchowderFood,chicchowderSat).setUnlocalizedName("chickencornchowder"));
		chickenchowder.setRegistryName("chickencornchowder");
		GameRegistry.register(chickenchowder);
		
		kernels.setUnlocalizedName("kernels");
		kernels.setRegistryName("kernels");
		GameRegistry.register(kernels);
		
		firework.setUnlocalizedName("popfirework");
		firework.setRegistryName("popfirework");
		GameRegistry.register(firework);
		EntityRegistry.registerModEntity(MyEntityFireworkRocket.class, "entitypopfirework", 132, this, 64, 3, true);
		
		MapGenStructureIO.registerStructureComponent(ComponentCornField.class, "Vicf");
		VillagerRegistry.instance().registerVillageCreationHandler(new CornWorldGen());


		Item popcorn = (Item)(new ItemPopcorn(popcornFood,popcornSat).setUnlocalizedName("poppedcorn"));

		popcorn.setRegistryName("poppedcorn");
		GameRegistry.register(popcorn);

		GameRegistry.addShapelessRecipe(new ItemStack(chowder),
				new Object[] {
			new ItemStack(Items.BOWL, 1),
			new ItemStack(Items.MILK_BUCKET, 1),
			new ItemStack(kernels, 1),
			new ItemStack(kernels, 1)
		});

		GameRegistry.addShapelessRecipe(new ItemStack(chickenchowder),
				new Object[] {
			new ItemStack(Items.BOWL, 1),
			new ItemStack(Items.MILK_BUCKET, 1),
			new ItemStack(Items.COOKED_CHICKEN, 1),
			new ItemStack(kernels, 1),
			new ItemStack(kernels, 1)

		});

		GameRegistry.addShapelessRecipe(new ItemStack(chickenchowder),
				new Object[] {
			new ItemStack(chowder, 1),
			new ItemStack(Items.COOKED_CHICKEN, 1)
		});

		GameRegistry.addShapelessRecipe(new ItemStack(kernels,2),
				new Object[] {
			new ItemStack(itemCob)
		});

		GameRegistry.addSmelting(itemCob, new ItemStack(itemRoasted), .2F);
		GameRegistry.addSmelting(kernels, new ItemStack(popcorn,2), .01F);
		MinecraftForge.addGrassSeed(new ItemStack(kernels), kernelWeight);
		
	}

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{

		MinecraftForge.EVENT_BUS.register(new ChickenFollowHandler());
		MinecraftForge.EVENT_BUS.register(new ChickenBreedHandler());
		MinecraftForge.EVENT_BUS.register(new PigFollowHandler());
		MinecraftForge.EVENT_BUS.register(new PigBreedHandler());
		MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
		
		if(Loader.isModLoaded("immersiveengineering")){
			try {
				Compatability.IE2();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		proxy.init(event);
	}
}
