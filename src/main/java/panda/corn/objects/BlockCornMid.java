package panda.corn.objects;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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
		return 3;
	}

	@Override
	public IBlockState getNextState() {
		return ModBlocks.CORN_TOP.getDefaultState();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CROPS_AABB[state.getValue(AGE) + 6];
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if (getAge(state) == getMaxAge()) drops.add(new ItemStack(ModItems.CORNCOB));
		//TODO: Add secondary cob drop.
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
