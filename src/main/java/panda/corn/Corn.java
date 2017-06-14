package panda.corn;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeFireworks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import panda.corn.events.GenericBreedHandler;
import panda.corn.events.GenericFollowHandler;
import panda.corn.events.RemapHandler;
import panda.corn.events.ToolTipHandler;
import panda.corn.other.Compatability;
import panda.corn.other.Config;
import panda.corn.other.MyRecipeFireworks;
import panda.corn.registry.MasterRegistrar;
import panda.corn.registry.ObjectList;

@Mod(modid = Corn.MODID, name = Corn.NAME, version = Corn.VERSION)

public class Corn {	
	
	public static final String MODID = "simplecorn";
	public static final String NAME = "Simple Corn";
	public static final String VERSION = "2.2.0";
	
	public static boolean isIEInstalled = Loader.isModLoaded("immersiveengineering");
	
	public static Logger log;
	
	
	@Instance(MODID)
	public static Corn instance;
		
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		log = LogManager.getLogger(Corn.NAME);
		Config.preInit(event);
		MasterRegistrar.callRegistry(event);
		ReplaceFireworkRecipe();
		    	
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(EntityPig.class, ObjectList.COB));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(EntityPig.class,ObjectList.COB));
		
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(EntityChicken.class,ObjectList.KERNELS));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(EntityChicken.class,ObjectList.KERNELS));
		MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
		MinecraftForge.EVENT_BUS.register(new RemapHandler());
		
		if(isIEInstalled){
			Compatability.IE2();
		}
	}
	
	@EventHandler
    public void missingMapping(FMLMissingMappingsEvent event)
    {
		RemapHandler.processingMissingMap(event);
    }
	

	private void ReplaceFireworkRecipe(){
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
	}
}
