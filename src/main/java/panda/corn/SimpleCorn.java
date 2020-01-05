package panda.corn;

import java.util.Set;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import panda.corn.entity.MyEntityFireworkRocket;
import panda.corn.events.ToolTipHandler;
import panda.corn.gen.ComponentCornField;
import panda.corn.gen.CornWorldGen;
import panda.corn.init.ModItems;
import panda.corn.other.ImmersiveEngineeringCompat;
import panda.corn.other.ThermalCompat;
import panda.corn.proxy.CommonProxy;

@Mod(modid = SimpleCorn.MODID, name = SimpleCorn.NAME, version = SimpleCorn.VERSION, dependencies = "after:immersiveengineering;"+"after:thermalexpansion")

public class SimpleCorn {	
	
	public static final String MODID = "simplecorn";
	public static final String NAME = "Simple Corn";
	public static final String VERSION = "2.5.12";
	
	private static boolean isIEInstalled;
	private static boolean isThermalInstalled;
	public Configuration config;
	
	@Instance(MODID)
	public static SimpleCorn instance;
	
	@SidedProxy(clientSide = "panda.corn.proxy.ClientProxy", serverSide = "panda.corn.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		MapGenStructureIO.registerStructureComponent(ComponentCornField.class, "Vicf");
		VillagerRegistry.instance().registerVillageCreationHandler(new CornWorldGen());
		
		config = new Configuration(event.getSuggestedConfigurationFile());
		ConfigSimpleCorn.load(config);
		
		if(ConfigSimpleCorn.popcornFireworks){
		  MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
		}
		
		EntityRegistry.registerModEntity(new ResourceLocation(MODID+":entitypopfirework"),MyEntityFireworkRocket.class, "entitypopfirework", 132, instance, 64, 3, true);	
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		if(isIEInstalled){
			ImmersiveEngineeringCompat.init();
		}
		
		if(isThermalInstalled){
			ThermalCompat.init();
		}
		
		if(ConfigSimpleCorn.popcornFireworks){
		  RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).remove(new ResourceLocation("minecraft:fireworks"));
		}
		
		((Set<Item>) ObfuscationReflectionHelper.getPrivateValue(EntityPig.class, null, "field_184764_bw")).add(ModItems.CORNCOB);
		((Set<Item>) ObfuscationReflectionHelper.getPrivateValue(EntityChicken.class, null, "field_184761_bD")).add(ModItems.KERNELS);
		
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.KERNELS), ConfigSimpleCorn.kernelWeight);
		VillagerRegistry.FARMER.getCareer(0).addTrade(1, new EntityVillager.EmeraldForItems(ModItems.CORNCOB, new EntityVillager.PriceInfo(18, 22)));
	}
	
	@EventHandler
	public static void onConstructionEvent(FMLConstructionEvent event) {
		isIEInstalled = Loader.isModLoaded("immersiveengineering");
		isThermalInstalled = Loader.isModLoaded("thermalexpansion");
	}

}
