package panda.corn.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import panda.corn.SimpleCorn;

@EventBusSubscriber(modid = SimpleCorn.MODID)
public class ConfigSimpleCorn {
	
	public static int ethanolvolume;
	public static int plantoilvolume;
	public static int clochedropamount;
	public static boolean useeasyharvesting;
	
	public static final ForgeConfigSpec SERVER_SPEC;
	static final ServerConfig SERVER;

	static {
		{
			final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
			SERVER = specPair.getLeft();
			SERVER_SPEC = specPair.getRight();
		}
	}
	
	public static void bakeServer(final ModConfig config) {

		useeasyharvesting = SERVER.useeasyharvesting.get();
		ethanolvolume = SERVER.ethanolvolume.get();
		plantoilvolume = SERVER.plantoilvolume.get();
		clochedropamount = SERVER.clochedropamount.get();
	}
}
