//package panda.corn.gen;
//
//import java.util.List;
//import java.util.Random;
//
//import net.minecraft.util.Direction;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.gen.feature.structure.StructurePiece;
//import net.minecraft.world.gen.feature.structure.VillagePieces.Village;
//import net.minecraft.world.gen.feature.structure.VillageStructure.Start;
//import panda.corn.ConfigSimpleCorn;
//
//public class CornWorldGen implements IVillageCreationHandler {
//
//	@Override
//	public PieceWeight getVillagePieceWeight(Random random, int i) {
//        return  new StructureVillagePieces.PieceWeight(ComponentCornField.class, ConfigSimpleCorn.generationWeight, MathHelper.getInt(random, 2 + i, 4 + i * 2));
//	}
//
//	@Override
//	public Class<?> getComponentClass() {
//		return ComponentCornField.class;
//	}
//
//	@Override
//	public Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructurePiece> pieces, Random random, int x, int y, int z, Direction facing, int p5) {
//		return ComponentCornField.createPiece(startPiece, pieces, random, x, y, z, facing, p5);
//	}
//}
