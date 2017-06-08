package panda.corn;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeFireworks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import panda.corn.entity.MyEntityFireworkRocket;
import panda.corn.events.GenericBreedHandler;
import panda.corn.events.GenericFollowHandler;
import panda.corn.events.ToolTipHandler;
import panda.corn.other.Compatability;
import panda.corn.other.Config;
import panda.corn.other.MyRecipeFireworks;
import panda.corn.other.RenderFireworkEntity;
import panda.corn.registry.MasterRegistrar;
import panda.corn.registry.ObjectList;

@Mod(modid = Corn.MODID, name = Corn.NAME, version = Corn.VERSION)

public class Corn {	
	
	public static final String MODID = "simplecorn";
	public static final String NAME = "Simple Corn";
	public static final String VERSION = "2.1.9";
	
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
		if(event.getSide() == Side.CLIENT){
			RenderingRegistry.registerEntityRenderingHandler(MyEntityFireworkRocket.class, RenderFireworkEntity.INSTANCE);
		}
		
		    	
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(EntityPig.class, ObjectList.COB));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(EntityPig.class,ObjectList.COB));
		
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(EntityChicken.class,ObjectList.KERNELS));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(EntityChicken.class,ObjectList.KERNELS));
		MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
		
		if(isIEInstalled){
			Compatability.IE2();
		}
	}
	
	@EventHandler
    public void missingMapping(FMLMissingMappingsEvent event)
    {
		List<MissingMapping> missingMappings = event.getAll();
        for (MissingMapping map : missingMappings)
        {
        	if(map.name.equals("corn:corn")){
        		map.remap(ObjectList.CORN);
        	}else
        	if(map.name.equals("corn:corncob")){
        		map.remap(ObjectList.COB);
        	}else
        	if(map.name.equals("corn:kernels")){
        		map.remap(ObjectList.KERNELS);
        	}else
        	if(map.name.equals("corn:poppedcorn")){
        		map.remap(ObjectList.POPCORN);
        	}else
        	if(map.name.equals("corn:roastedcorn")){
        		map.remap(ObjectList.ROASTED_CORN);
        	}else
        	if(map.name.equals("corn:popfirework")){
        		map.remap(ObjectList.POP_FIREWORK);
        	}else
        	if(map.name.equals("corn:chickencornchowder")){
        		map.remap(ObjectList.CHICKEN_CHOWDER);
        	}else
        	if(map.name.equals("corn:cornchowder")){
        		map.remap(ObjectList.CHOWDER);
        	}
        }
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
