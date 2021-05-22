package panda.corn.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import panda.corn.config.ConfigSimpleCorn;
import panda.corn.init.ModBlocks;
import panda.corn.init.ModItems;

public class BlockCornMid extends BlockCorn {

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos){
		BlockState dState = world.getBlockState(pos.down());
		return dState.getBlock() == ModBlocks.CORN && ModBlocks.CORN.isMaxAge(dState);
	}

	 @Override
	   @OnlyIn(Dist.CLIENT)
	   public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
	      return new ItemStack(ModItems.CORNCOB);
	   }

	@Override
	public int getMaxAge() {
		return 2;
	}

	@Override
	public BlockState getNextState() {
		return ModBlocks.CORN_TOP.getDefaultState();
	}
	
	@Override
	 public void grow(World worldIn, BlockPos pos, BlockState state) {
		isValidPosition(state, worldIn, pos); //Check and see if we can still exist.
		if (worldIn.getBlockState(pos) == state) //If we can:
		{
			if (!worldIn.isAreaLoaded(pos, 1)) //Make sure we should bother checking
				return;
			if (worldIn.getLightSubtracted(pos, 0) >= 9 && checkFertile(worldIn, pos)) //Check for light and water
			{
				boolean canGrow = worldIn.rand.nextInt(2) == 0;
				if (!isMaxAge(state)) {
					if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, canGrow)) {
						worldIn.setBlockState(pos, withAge(getAge(state) + 1));
						ForgeHooks.onCropsGrowPost(worldIn, pos, state);
					}
					 if (isMaxAge(worldIn.getBlockState(pos)) && getNextState() != null && worldIn.getBlockState(pos = pos.up()).getMaterial().isReplaceable()) {
						 if (ForgeHooks.onCropsGrowPre(worldIn, pos, getNextState(), canGrow)) {
							 worldIn.setBlockState(pos, getNextState());
							 ForgeHooks.onCropsGrowPost(worldIn, pos, getNextState());
						 }
					 }
				} else if (getNextState() != null && worldIn.getBlockState(pos = pos.up()).getMaterial().isReplaceable()) {
					if (ForgeHooks.onCropsGrowPre(worldIn, pos, getNextState(), canGrow)) {
						worldIn.setBlockState(pos, getNextState());
						ForgeHooks.onCropsGrowPost(worldIn, pos, getNextState());
					}
				}
			}
		}
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		isValidPosition(state, worldIn, pos); //Check and see if we can still exist.
		if (worldIn.getBlockState(pos) == state) //If we can:
		{
			if (!worldIn.isAreaLoaded(pos, 1)) //Make sure we should bother checking
				return;
			if (worldIn.getLightSubtracted(pos, 0) >= 9 && checkFertile(worldIn, pos)) //Check for light and water
			{
				boolean canGrow = rand.nextInt(10) == 0;
				if (!isMaxAge(state)) {
					if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, canGrow)) {
						worldIn.setBlockState(pos, withAge(getAge(state) + 1));
						ForgeHooks.onCropsGrowPost(worldIn, pos, state);
						
						//if (isMaxAge(worldIn.getBlockState(pos)) && getNextState() != null && worldIn.getBlockState(pos = pos.up()).getMaterial().isReplaceable()) {
						//	if (ForgeHooks.onCropsGrowPre(worldIn, pos, getNextState(), canGrow)) {
						//		worldIn.setBlockState(pos, getNextState());
						//		ForgeHooks.onCropsGrowPost(worldIn, pos, getNextState());
						//	}
						//}
					}
				} else if (getNextState() != null && worldIn.getBlockState(pos = pos.up()).getMaterial().isReplaceable()) {
					if (ForgeHooks.onCropsGrowPre(worldIn, pos, getNextState(), canGrow)) {
						worldIn.setBlockState(pos, getNextState());
						ForgeHooks.onCropsGrowPost(worldIn, pos, getNextState());
					}
				}
			}
		}
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(ConfigSimpleCorn.useeasyharvesting){
			if(state.get(this.getAgeProperty()) > getMaxAge()){
				worldIn.removeBlock(pos.down(),false);
				worldIn.setBlockState(pos.down(), ModBlocks.CORN.getDefaultState());
			}
		}
		
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	 public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		 return SHAPES[state.get(this.getAgeProperty())+6];
	 }

	@Override
	public boolean checkFertile(World world, BlockPos pos) {
		return isValidPosition(getDefaultState(), world, pos);
	}
	
	@Override
	public boolean ticksRandomly(BlockState state) {
		return state.get(this.getAgeProperty()) < 3;
	}

}
