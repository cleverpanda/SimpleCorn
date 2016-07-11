package panda.corn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class CornWorldGen implements IVillageCreationHandler {

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {
        return  new StructureVillagePieces.PieceWeight(ComponentCornField.class, 20, MathHelper.getRandomIntegerInRange(random, 2 + i, 4 + i * 2));
	}

	@Override
	public Class<?> getComponentClass() {
		return ComponentCornField.class;
	}

	@Override
	public Object buildComponent(PieceWeight villagePiece, Start startPiece,List pieces, Random random, int p1, int p2, int p3,EnumFacing facing, int p5) {
		return ComponentCornField.buildComponent(startPiece, pieces, random, p1, p2, p3, facing, p5);
	}
}
