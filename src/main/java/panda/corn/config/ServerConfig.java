package panda.corn.config;

import net.minecraftforge.common.ForgeConfigSpec;
import panda.corn.SimpleCorn;

final class ServerConfig {
	final ForgeConfigSpec.BooleanValue useeasyharvesting;

	final ForgeConfigSpec.IntValue ethanolvolume;
	final ForgeConfigSpec.IntValue plantoilvolume;
	final ForgeConfigSpec.IntValue clochedropamount;

	ServerConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		useeasyharvesting = builder.comment("Allow right click harvesting").translation(SimpleCorn.MODID + ".config.useEasyHarvesting").define("useEasyHarvesting", false);

		builder.push("immersive engineering");
		ethanolvolume = builder.comment("Amount of ethanol in mB that corn produces in an IE fermenter").translation(SimpleCorn.MODID + ".config.ethanolVolume").defineInRange("ethanolVolume", 120, 0, Integer.MAX_VALUE);
		plantoilvolume = builder.comment("Amount of plant oil in mB that kernels produces in an IE squeezer").translation(SimpleCorn.MODID + ".config.plantOilVolume").defineInRange("plantOilVolume", 100, 0, Integer.MAX_VALUE);
		clochedropamount = builder.comment("The number of corn cobs you get from growing corn in a garden cloche").translation(SimpleCorn.MODID + ".config.clocheDropAmount").defineInRange("clocheDropAmount", 2, 0, Integer.MAX_VALUE);

		builder.pop();
	}

}
