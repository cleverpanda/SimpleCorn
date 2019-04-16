package panda.corn;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import panda.corn.entity.MyEntityFireworkRocket;
import panda.corn.events.GenericBreedHandler;
import panda.corn.events.GenericFollowHandler;
import panda.corn.events.ToolTipHandler;
import panda.corn.gen.ComponentCornField;
import panda.corn.gen.CornWorldGen;
import panda.corn.init.ModItems;
import panda.corn.other.Compatability;
import panda.corn.proxy.CommonProxy;

@Mod(modid = Corn.MODID, name = Corn.NAME, version = Corn.VERSION, dependencies = "after:immersiveengineering")

public class Corn {	
	
	public static final String MODID = "simplecorn";
	public static final String NAME = "Simple Corn";
	public static final String VERSION = "2.5.0";
	
	public static boolean isIEInstalled;
	public Configuration config;
	
	@Instance(MODID)
	public static Corn instance;
	
	@SidedProxy(clientSide = "panda.corn.proxy.ClientProxy", serverSide = "panda.corn.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		MapGenStructureIO.registerStructureComponent(ComponentCornField.class, "Vicf");
		VillagerRegistry.instance().registerVillageCreationHandler(new CornWorldGen());
		config = new Configuration(event.getSuggestedConfigurationFile());
		ConfigSimpleCorn.load(config);
		
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(EntityPig.class, ModItems.COB));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(EntityPig.class,ModItems.COB));
		
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(EntityChicken.class,ModItems.KERNELS));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(EntityChicken.class,ModItems.KERNELS));
		MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
		
		EntityRegistry.registerModEntity(new ResourceLocation(MODID+":entitypopfirework"),MyEntityFireworkRocket.class, "entitypopfirework", 132, instance, 64, 3, true);
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		if(Corn.isIEInstalled){
			Compatability.immersiveEngineering();
		}
		
		proxy.registerOreDicts();
		
		GameRegistry.addSmelting(ModItems.COB, new ItemStack(ModItems.ROASTED_CORN), .2F);
		GameRegistry.addSmelting(ModItems.KERNELS, new ItemStack(ModItems.POPCORN,2), .01F);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.KERNELS), ConfigSimpleCorn.kernelWeight);
		RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).remove(new ResourceLocation("minecraft:fireworks"));
		VillagerRegistry.FARMER.getCareer(0).addTrade(1, new EntityVillager.EmeraldForItems(ModItems.COB, new EntityVillager.PriceInfo(18, 22)));
	}
	
	@EventHandler
	public void onConstructionEvent(FMLConstructionEvent event) {
		isIEInstalled = Loader.isModLoaded("immersiveengineering");
	}

}
