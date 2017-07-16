package panda.corn;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeFireworks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import panda.corn.events.GenericBreedHandler;
import panda.corn.events.GenericFollowHandler;
import panda.corn.events.ToolTipHandler;
import panda.corn.other.Compatability;
import panda.corn.other.Config;
import panda.corn.other.MyRecipeFireworks;
import panda.corn.registry.MasterRegistrar;
import panda.corn.registry.ObjectList;

@Mod(modid = Corn.MODID, name = Corn.NAME, version = Corn.VERSION, dependencies = "after:immersiveengineering")

public class Corn {	
	
	public static final String MODID = "simplecorn";
	public static final String NAME = "Simple Corn";
	public static final String VERSION = "2.3.1";
	
	public static final boolean ISIEINSTALLED = Loader.isModLoaded("immersiveengineering");
	
	@Instance(MODID)
	public static Corn instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.preInit(event);
		MasterRegistrar.callRegistry(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(EntityPig.class, ObjectList.COB));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(EntityPig.class,ObjectList.COB));
		
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(EntityChicken.class,ObjectList.KERNELS));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(EntityChicken.class,ObjectList.KERNELS));
		MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
		
		if(ISIEINSTALLED){
			Compatability.immersiveEngineering();
		}

		RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).remove(new ResourceLocation("minecraft:fireworks"));
	}
}
