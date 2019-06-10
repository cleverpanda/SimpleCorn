package panda.corn;

import static net.minecraftforge.fml.Logging.CORE;
import static net.minecraftforge.fml.loading.LogMarkers.FORGEMOD;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfig.Server;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = SimpleCorn.MODID)
public class ConfigSimpleCorn {
//	
//	public static class Config {
//	public final int dropamount;
//	public final int cobFood;
//	public final float cobSat;
//	public final int roastedFood;
//	public final float roastedSat;
//	public final int chowderFood;
//	public final int chicchowderFood;
//	public final int popcornFood;
//	public final float popcornSat;
//	public final int kernelWeight;
//	public final int generationWeight;
//	public final int growChance;
//	public final int ethanolvolume;
//	public final int numkernels;
//	public final int plantoilvolume;
//	public final int clochedropamount;
//	public final boolean useeasyharvesting;
//	public final boolean popcornFireworks;
//	
//	Config(ForgeConfigSpec.Builder builder) {
//        builder.comment("Server configuration settings")
//               .push("server");
//
//        removeErroringEntities = builder
//                .comment("Set this to true to remove any Entity that throws an error in its update method instead of closing the server and reporting a crash log. BE WARNED THIS COULD SCREW UP EVERYTHING USE SPARINGLY WE ARE NOT RESPONSIBLE FOR DAMAGES.")
//                .translation("forge.configgui.removeErroringEntities")
//                .worldRestart()
//                .define("removeErroringEntities", false);
//
//        removeErroringTileEntities = builder
//                .comment("Set this to true to remove any TileEntity that throws an error in its update method instead of closing the server and reporting a crash log. BE WARNED THIS COULD SCREW UP EVERYTHING USE SPARINGLY WE ARE NOT RESPONSIBLE FOR DAMAGES.")
//                .translation("forge.configgui.removeErroringTileEntities")
//                .worldRestart()
//                .define("removeErroringTileEntities", false);
//
//        fullBoundingBoxLadders = builder
//                .comment("Set this to true to check the entire entity's collision bounding box for ladders instead of just the block they are in. Causes noticeable differences in mechanics so default is vanilla behavior. Default: false")
//                .translation("forge.configgui.fullBoundingBoxLadders")
//                .worldRestart()
//                .define("fullBoundingBoxLadders", false);
//
//        zombieBaseSummonChance = builder
//                .comment("Base zombie summoning spawn chance. Allows changing the bonus zombie summoning mechanic.")
//                .translation("forge.configgui.zombieBaseSummonChance")
//                .worldRestart()
//                .defineInRange("zombieBaseSummonChance", 0.1D, 0.0D, 1.0D);
//
//        zombieBabyChance = builder
//                .comment("Chance that a zombie (or subclass) is a baby. Allows changing the zombie spawning mechanic.")
//                .translation("forge.configgui.zombieBabyChance")
//                .worldRestart()
//                .defineInRange("zombieBabyChance", 0.05D, 0.0D, 1.0D);
//
//        logCascadingWorldGeneration = builder
//                .comment("Log cascading chunk generation issues during terrain population.")
//                .translation("forge.configgui.logCascadingWorldGeneration")
//                .define("logCascadingWorldGeneration", true);
//
//        fixVanillaCascading = builder
//                .comment("Fix vanilla issues that cause worldgen cascading. This DOES change vanilla worldgen so DO NOT report bugs related to world differences if this flag is on.")
//                .translation("forge.configgui.fixVanillaCascading")
//                .define("fixVanillaCascading", false);
//
//        dimensionUnloadQueueDelay = builder
//                .comment("The time in ticks the server will wait when a dimension was queued to unload. This can be useful when rapidly loading and unloading dimensions, like e.g. throwing items through a nether portal a few time per second.")
//                .translation("forge.configgui.dimensionUnloadQueueDelay")
//                .defineInRange("dimensionUnloadQueueDelay", 0, 0, Integer.MAX_VALUE);
//
//        clumpingThreshold = builder
//                .comment("Controls the number threshold at which Packet51 is preferred over Packet52, default and minimum 64, maximum 1024")
//                .translation("forge.configgui.clumpingThreshold")
//                .worldRestart()
//                .defineInRange("clumpingThreshold", 64, 64, 1024);
//
//        builder.pop();
//    }
//}
//	
//    static final ForgeConfigSpec serverSpec;
//    public static final Config CONFIG;
//    static {
//        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
//        serverSpec = specPair.getRight();
//        CONFIG = specPair.getLeft();
//    }
//
//    @SubscribeEvent
//    public static void onLoad(final ModConfig.Loading configEvent) {
//        LogManager.getLogger().debug(FORGEMOD, "Loaded forge config file {}", configEvent.getConfig().getFileName());
//    }
//
//    @SubscribeEvent
//    public static void onFileChange(final ModConfig.ConfigReloading configEvent) {
//        LogManager.getLogger().fatal(CORE, "Forge config just got changed on the file system!");
//    }
}
