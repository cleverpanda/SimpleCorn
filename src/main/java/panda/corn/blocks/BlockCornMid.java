package panda.corn.blocks;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import panda.corn.ConfigSimpleCorn;
import panda.corn.init.ModBlocks;
import panda.corn.init.ModItems;

public class BlockCornMid extends BlockCorn {

	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
		IBlockState dState = world.getBlockState(pos.down());
		return dState.getBlock() == ModBlocks.CORN && ModBlocks.CORN.isMaxAge(dState);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ModItems.CORNCOB);
	}

	@Override
	public int getMaxAge() {
		return 2;
	}

	@Override
	public IBlockState getNextState() {
		return ModBlocks.CORN_TOP.getDefaultState();
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		this.checkAndDropBlock(worldIn, pos, state); //Check and see if we can still exist.
		if (worldIn.getBlockState(pos) == state) //If we can:
		{
			if (!worldIn.isAreaLoaded(pos, 1)) //Make sure we should bother checking
				return;
			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && checkFertile(worldIn, pos)) //Check for light and water
			{
				boolean canGrow = rand.nextInt(ConfigSimpleCorn.growChance) == 0;
				if (!isMaxAge(state)) {
					if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, canGrow)) {
						worldIn.setBlockState(pos, withAge(getAge(state) + 1));
						
						ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
						if (isMaxAge(worldIn.getBlockState(pos)) && getNextState() != null && worldIn.getBlockState(pos = pos.up()).getBlock().isReplaceable(worldIn, pos)) {
							if (ForgeHooks.onCropsGrowPre(worldIn, pos, getNextState(), canGrow)) {
								worldIn.setBlockState(pos, getNextState());
								ForgeHooks.onCropsGrowPost(worldIn, pos, getNextState(), worldIn.getBlockState(pos));
							}
						}
					}
				} else if (getNextState() != null && worldIn.getBlockState(pos = pos.up()).getBlock().isReplaceable(worldIn, pos)) {
					if (ForgeHooks.onCropsGrowPre(worldIn, pos, getNextState(), canGrow)) {
						worldIn.setBlockState(pos, getNextState());
						ForgeHooks.onCropsGrowPost(worldIn, pos, getNextState(), worldIn.getBlockState(pos));
					}
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(ConfigSimpleCorn.useeasyharvesting){
			if(isMaxAge(state)){
				return worldIn.setBlockState(pos.down(), ModBlocks.CORN.getDefaultState());
			}
		}
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);		
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CROPS_AABB[state.getValue(AGE) + 6];
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		
		if (getAge(state) == getMaxAge()+1){
			int n = rand.nextInt(ConfigSimpleCorn.dropamount) +1;
			drops.add(new ItemStack(ModItems.CORNCOB,n));
		}else
			drops.add(new ItemStack(ModItems.KERNELS));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return withAge(meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return getAge(state);
	}

	@Override
	public boolean checkFertile(World world, BlockPos pos) {
		return canBlockStay(world, pos, getDefaultState());
	}

}
