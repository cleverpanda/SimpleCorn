package panda.corn;

import net.minecraftforge.common.config.Configuration;

public class ConfigSimpleCorn {
	
	public static int dropRolls;
	public static int cobFood;
	public static float cobSat;
	public static int roastedFood;
	public static float roastedSat;
	public static int chowderFood;
	public static int chicchowderFood;
	public static int popcornFood;
	public static float popcornSat;
	public static int kernelWeight;
	public static int generationWeight;
	public static int growChance;
	public static int ethanolvolume;
	public static int numkernels;
	public static int plantoilvolume;
	public static int clochedropamount;
	public static boolean useeasyharvesting;
	public static boolean PopcornFireworks;

	public static void load(Configuration config) {
		config.load();
		String ieCategory = "Immersive Engineering";
		cobFood = config.getInt("VALUE_FOOD_COB", Configuration.CATEGORY_GENERAL, 1, 1, 20,"Hunger value of corn cobs");
		cobSat = config.getFloat("VALUE_SATURATION_COB",  Configuration.CATEGORY_GENERAL, 0.3F, 0F, 1F, "Saturation value of corn cobs");
		
		roastedFood = config.getInt("VALUE_FOOD_ROASTED_CORN", Configuration.CATEGORY_GENERAL, 6, 1, 20,"Hunger value of roasted corn");
		roastedSat = config.getFloat("VALUE_SATURATION_ROASTED_CORN",  Configuration.CATEGORY_GENERAL, 0.6F, 0F, 1F, "Saturation value of roasted corn");
		
		chowderFood = config.getInt("VALUE_FOOD_CHOWDER", Configuration.CATEGORY_GENERAL, 7, 1, 20,"Hunger value of corn chowder");		
		chicchowderFood = config.getInt("VALUE_FOOD_CHICKEN_CHOWDER", Configuration.CATEGORY_GENERAL, 10, 1, 20,"Hunger value of chicken corn chowder");
		
		popcornFood = config.getInt("VALUE_FOOD_POPCORN", Configuration.CATEGORY_GENERAL, 1, 1, 20,"Hunger value of popcorn");
		popcornSat = config.getFloat("VALUE_SATURATION_POPCORN",  Configuration.CATEGORY_GENERAL, 0.1F, 0F, 1F, "Saturation value of popcorn");
		
		kernelWeight =  config.getInt("VALUE_KERNEL_DROP", Configuration.CATEGORY_GENERAL, 8, 1, 100,"The relative chance of dropping kernels from grass. Seeds are 10");
		generationWeight =  config.getInt("VALUE_CORN_FIELD_GENERATION", Configuration.CATEGORY_GENERAL, 30, 0, 100,"The relative chance of spawning corn fields. The small houses are 3, Blacksmiths are 15. higher is lower.");
		growChance = config.getInt("VALUE_GROWTH_CHANCE", Configuration.CATEGORY_GENERAL, 3, 0, 1000,"Chance of growing corn from rand.nextInt(n) == 0");
		dropRolls = config.getInt("VALUE_DROP_ROLLS", Configuration.CATEGORY_GENERAL, 1, 1, 1000,"How many times each block should do a random check for drops");
		
		useeasyharvesting = config.getBoolean("USE_EASY_HARVESTING", Configuration.CATEGORY_GENERAL, false, "Allow right click harvesting");
		PopcornFireworks = config.getBoolean("ENABLE_POPCORN_FIREWORKS", Configuration.CATEGORY_GENERAL, true, "");
				
		plantoilvolume = config.getInt("VALUE_IE_FESQUEEZER_OIL_VOLUME",  ieCategory, 100, 0, 1000, "Amount of plant oil in mB that kernels produces in an IE squeezer");
		ethanolvolume = config.getInt("VALUE_IE_FERMENTER_ETHANOL_VOLUME",  ieCategory, 120, 0, 1000, "Amount of ethanol in mB that corn produces in an IE fermenter");
		clochedropamount = config.getInt("VALUE_NUM_DROPS_CLOCHE",ieCategory,2,0,100,"The number of corn cobs you get from growing corn in a garden cloche");
		if (config.hasChanged()) config.save();
	}
}
