package panda.corn;

import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import panda.corn.events.GenericBreedHandler;
import panda.corn.events.GenericFollowHandler;
import panda.corn.init.ModItems;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SimpleCorn.MODID)

public class SimpleCorn {	
	//modid = SimpleCorn.MODID, name = SimpleCorn.NAME, version = SimpleCorn.VERSION, dependencies = "after:immersiveengineering;"+"after:thermalexpansion"
	public static final String MODID = "simplecorn";
	public static final String NAME = "Simple Corn";
	public static final String VERSION = "2.6.0";

	
	public SimpleCorn(){
	    //ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigSimpleCorn.Config);
		
	}

	@SubscribeEvent
	public void setup(final FMLCommonSetupEvent event) {
		//MapGenStructureIO.registerStructureComponent(ComponentCornField.class, "Vicf");
		//VillagerRegistry.instance().registerVillageCreationHandler(new CornWorldGen());
		//config = new Configuration(event.getSuggestedConfigurationFile());
		//ConfigSimpleCorn.load(config);
		
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(PigEntity.class, ModItems.CORNCOB));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(PigEntity.class,ModItems.CORNCOB));
		
		MinecraftForge.EVENT_BUS.register(new GenericFollowHandler(ChickenEntity.class,ModItems.KERNELS));
		MinecraftForge.EVENT_BUS.register(new GenericBreedHandler(ChickenEntity.class,ModItems.KERNELS));
		//VillagerTrades.
		//MinecraftForge.addGrassSeed(new ItemStack(ModItems.KERNELS), 8);
		//VillagerRegistry.FARMER.getCareer(0).addTrade(1, new EntityVillager.EmeraldForItems(ModItems.CORNCOB, new EntityVillager.PriceInfo(18, 22)));
	}

	//CookingRecipeBuilder.func_218629_c(Ingredient.fromItems(ModItems.CORNCOB), ModItems.ROASTEDCORN, 0.20F, 200);
	//CookingRecipeBuilder.func_218629_c(Ingredient.fromItems(ModItems.KERNELS), ModItems.POPPEDCORN, 0.01F, 200);
	
}
