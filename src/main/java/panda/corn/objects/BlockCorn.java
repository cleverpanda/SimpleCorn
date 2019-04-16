package panda.corn.objects;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import panda.corn.ConfigSimpleCorn;
import panda.corn.init.ModBlocks;
import panda.corn.init.ModItems;

public class BlockCorn extends BlockCrops implements IGrowable {

	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);

	public static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {

			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), //0
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), //1
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), //2
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), //3
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), //4
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), //9

			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), //5
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), //6
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), //7
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, .875D, 1.0D), //10

			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), //8
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) }; //11

	public BlockCorn() {
		setHardness(0.3F);
		this.setSoundType(SoundType.PLANT);
		this.disableStats();
	}

	@Override
	public float getBlockHardness(IBlockState state, World worldIn, BlockPos pos) {
		return isMaxAge(state) ? 0.2f : 0.0f;
	}

	@Override
	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!canBlockStay(worldIn, pos, state)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if (getAge(state) != getMaxAge()) drops.add(new ItemStack(ModItems.KERNELS));
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
		return this.canPlaceBlockAt(world, pos);
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		return !isMaxAge(state) && world.getBlockState(pos.up()).getMaterial().isReplaceable();
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		IBlockState down = world.getBlockState(pos.down());
		return down.getBlock().canSustainPlant(down, world, pos, EnumFacing.UP, this);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, getAgeProperty());
	}

	@Override
	protected PropertyInteger getAgeProperty() {
		return AGE;
	}

	@Override
	protected int getBonemealAgeIncrease(World worldIn) {
		return MathHelper.getInt(worldIn.rand, 1, 2);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CROPS_AABB[state.getValue(AGE)];
	}

	@Override
	protected Item getCrop() {
		return ModItems.CORNCOB;
	}

	@Override
	public int getMaxAge() {
		return 5;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return getAge(state) == 5 ? 15 : getAge(state);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ModItems.KERNELS);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		if (meta <= 4) return withAge(meta);
		if (meta == 9 || meta == 15) return withAge(5);

		if (meta <= 7) return ModBlocks.CORN_MID.withAge(meta - 5);
		if (meta == 10) return ModBlocks.CORN_MID.withAge(3);

		if (meta == 8) return ModBlocks.CORN_TOP.withAge(0);
		if (meta == 11) return ModBlocks.CORN_TOP.withAge(1);
		return getDefaultState();
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		updateTick(worldIn, pos, state, rand);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		//TODO: Re-implement: if(!ConfigSimpleCorn.useeasyharvesting)
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	public IBlockState getNextState() {
		return ModBlocks.CORN_MID.getDefaultState();
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

	public boolean checkFertile(World world, BlockPos pos) {
		return world.getBlockState(pos.down()).getBlock().isFertile(world, pos.down());
	}

}
