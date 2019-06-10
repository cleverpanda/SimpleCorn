//package panda.corn.gen;
//
//import java.util.List;
//import java.util.Random;
//
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.util.Direction;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.World;
//import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
//import net.minecraft.world.gen.feature.structure.StructurePiece;
//import net.minecraft.world.gen.feature.structure.VillageStructure.Start;
//import panda.corn.blocks.BlockCorn;
//import panda.corn.init.ModBlocks;
//
//public class ComponentCornField extends AbstractVillagePiece{
//
//	private BlockCorn crop;
//
//	public ComponentCornField(Start start, int type, Random rand, StructureBoundingBox box, Direction facing)
//	{
//		super(start, type);
//		this.setCoordBaseMode(facing);
//		this.boundingBox = box;
//		this.crop = ModBlocks.CORN;
//	}
//
//	@Override
//	public boolean addComponentParts(World worldIn, Random rand, StructureBoundingBox box)
//	{
//		if (this.averageGroundLvl < 0)
//        {
//            this.averageGroundLvl = this.getAverageGroundLevel(worldIn, box);
//
//            if (this.averageGroundLvl < 0)
//            {
//                return true;
//            }
//
//            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 3, 0);
//        }
//		BlockState edgestate = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
//		
//		this.fillWithBlocks(worldIn, box, 0, 1, 0, 6, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
//		this.fillWithBlocks(worldIn, box, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
//		this.fillWithBlocks(worldIn, box, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
//		this.fillWithBlocks(worldIn, box, 0, 0, 0, 0, 0, 8, edgestate, edgestate, false);
//		this.fillWithBlocks(worldIn, box, 6, 0, 0, 6, 0, 8, edgestate, edgestate, false);
//		this.fillWithBlocks(worldIn, box, 1, 0, 0, 5, 0, 0, edgestate, edgestate, false);
//		this.fillWithBlocks(worldIn, box, 1, 0, 8, 5, 0, 8, edgestate, edgestate, false);
//		this.fillWithBlocks(worldIn, box, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);
//		int i;
//		if(crop != null){
//			for (i = 1; i <= 7; ++i)
//			{
//				this.setBlockState(worldIn, crop.getDefaultState().withProperty(BlockCorn.CORNAGE,MathHelper.nextInt(rand, 0, 3)), 1, 1, i, box);
//				this.setBlockState(worldIn, crop.getDefaultState().withProperty(BlockCorn.CORNAGE,MathHelper.nextInt(rand, 0, 3)), 2, 1, i, box);
//				this.setBlockState(worldIn, crop.getDefaultState().withProperty(BlockCorn.CORNAGE,MathHelper.nextInt(rand, 0, 3)), 4, 1, i, box);
//				this.setBlockState(worldIn, crop.getDefaultState().withProperty(BlockCorn.CORNAGE,MathHelper.nextInt(rand, 0, 3)), 5, 1, i, box);
//			}
//		}
//
//		for (i = 0; i < 9; ++i)
//		{
//			for (int j = 0; j < 7; ++j)
//			{
//				this.clearCurrentPositionBlocksUpwards(worldIn, j, 4, i, box);
//				this.replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), j, -1, i, box);
//			}
//		}
//
//		return true;
//	}
//
//	public static ComponentCornField createPiece(Start start, List<StructurePiece> listin, Random rand, int x, int y, int z, Direction facing, int type)
//	{
//		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 7, 4, 9, facing);
//		return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(listin, structureboundingbox) == null ? new ComponentCornField(start, type, rand, structureboundingbox, facing) : null;
//	}
//
//}
//
//
