package panda.corn.init;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern.PlacementBehaviour;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.DesertVillagePools;
import net.minecraft.world.gen.feature.structure.PlainsVillagePools;
import net.minecraft.world.gen.feature.structure.SavannaVillagePools;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import panda.corn.SimpleCorn;


//Code modified from BluSunrize Copyright 2019
//https://github.com/BluSunrize/ImmersiveEngineering/blob/1.16.5/src/main/java/blusunrize/immersiveengineering/common/world/Villages.java
//Thanks, Blu!

public class Villages {
	
	
//	public static void init()
//	{
//		PlainsVillagePools.init();
//		SavannaVillagePools.init();
//		DesertVillagePools.init();
//		
//
//		// Register engineer's houses for each biome
//		for(String biome : new String[]{"plains", "savanna", "desert"}) {
//			addToPool(new ResourceLocation("village/"+biome+"/houses"), new ResourceLocation(SimpleCorn.MODID, "village/houses/"+biome+"_engineer"), 4);
//		}
//
//
//
//	}
//	
//	private static void addToPool(ResourceLocation pool, ResourceLocation toAdd, int weight)
//	{
//		JigsawPattern old = WorldGenRegistries.JIGSAW_POOL.getOrDefault(pool);
//
//		// Fixed seed to prevent inconsistencies between different worlds
//		List<JigsawPiece> shuffled;
//		if(old!=null)
//			shuffled = old.getShuffledPieces(new Random(0));
//		else
//			shuffled = ImmutableList.of();
//		Object2IntMap<JigsawPiece> newPieces = new Object2IntLinkedOpenHashMap<>();
//		for(JigsawPiece p : shuffled)
//			newPieces.computeInt(p, (JigsawPiece pTemp, Integer i) -> (i==null?0: i)+1);
//		newPieces.put(SingleJigsawAccess.construct(Either.left(toAdd), () -> ProcessorLists.EMPTY, PlacementBehaviour.RIGID), weight);
//		List<Pair<JigsawPiece, Integer>> newPieceList = newPieces.object2IntEntrySet().stream()
//				.map(e -> Pair.of(e.getKey(), e.getIntValue()))
//				.collect(Collectors.toList());
//
//		ResourceLocation name = old.getName();
//		Registry.register(WorldGenRegistries.JIGSAW_POOL, pool, new JigsawPattern(pool, name, newPieceList));
//	}
	
}
