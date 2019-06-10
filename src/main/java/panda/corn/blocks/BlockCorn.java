package panda.corn.blocks;

import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import panda.corn.ConfigSimpleCorn;
import panda.corn.init.ModBlocks;
import panda.corn.init.ModItems;

public class BlockCorn extends CropsBlock implements IGrowable {

	public static final IntegerProperty CORNAGE = BlockStateProperties.AGE_0_5;

	 protected static final VoxelShape[] SHAPES = new VoxelShape[]{
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, .875D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, .875D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)
	};


	 public BlockCorn(){
		 super(Block.Properties.create(Material.PLANTS).doesNotBlockMovement()
				 .tickRandomly().hardnessAndResistance(0.3F,0F).sound(SoundType.CROP));
	 }

	 @Override
	 public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext p_220053_4_) {
		 return SHAPES[state.get(this.getAgeProperty())];
	 }

	 @Override
	 protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		 return state.getBlock() instanceof FarmlandBlock;
	 }

	 @Override
	 public IntegerProperty getAgeProperty() {
		 return CORNAGE;
	 }	   

	 @Override
	 @OnlyIn(Dist.CLIENT)
	 protected IItemProvider getSeedsItem() {
		 return ModItems.KERNELS;
	 }

	 @Override
	 public void onPlayerDestroy(IWorld world, BlockPos pos, BlockState state) {

		 if(world.getBlockState(pos.down()).getBlock() == ModBlocks.CORN_MID){
			 world.destroyBlock(pos.down(), true);
			 world.destroyBlock(pos.down(2), true);
		 }else
			 if(world.getBlockState(pos.down()).getBlock() == ModBlocks.CORN){
				 world.destroyBlock(pos.down(), true);
			 }    
	 }

	 @Override
	 public float getBlockHardness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		 return isMaxAge(state) ? 0.2f : 0.0f;
	 }

	 @Override
	 public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		 BlockState down = world.getBlockState(pos.down());
		 return down.getBlock().canSustainPlant(down, world, pos, Direction.UP, this);
	 }

	 @Override
	 public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
		 return !isMaxAge(state) && world.getBlockState(pos.up()).getMaterial().isReplaceable();
	 }

	 @Override
	 public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		 return true;
	 }

	 @Override
	 protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder){
		 builder.add(getAgeProperty());
	 }

	 @Override
	 protected int getBonemealAgeIncrease(World worldIn) {
		 return MathHelper.nextInt(worldIn.rand, 1, 2);
	 }

	 @Override
	 public int getMaxAge() {
		 return 4;
	 }

	 
	 public int getMetaFromState(BlockState state) {
		 return getAge(state) == 5 ? 15 : getAge(state);
	 }

	 @Override
	   @OnlyIn(Dist.CLIENT)
	   public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
	      return new ItemStack(ModItems.KERNELS);
	   }

	 
	 public BlockState getStateFromMeta(int meta) {

		 if (meta <= 4) return withAge(meta);
		 if (meta == 9 || meta == 15) return withAge(5);

		 if (meta <= 7) return ModBlocks.CORN_MID.withAge(meta - 5);
		 if (meta == 10) return ModBlocks.CORN_MID.withAge(3);

		 if (meta == 8) return ModBlocks.CORN_TOP.withAge(0);
		 if (meta == 11) return ModBlocks.CORN_TOP.withAge(1);
		 return getDefaultState();
	 }

	 @Override
	 public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
		 tick(state, worldIn, pos, rand);
	 }

	 @Override
	 public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){

		 if(false){
			 if(state.get(this.getAgeProperty()) == 5){
				 worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				 return worldIn.setBlockState(pos, this.getDefaultState());
			 }
		 }

		 return super.onBlockActivated(state, worldIn, pos, player, hand, hit);		
	 }

	 public BlockState getNextState() {
		 return ModBlocks.CORN_MID.getDefaultState();
	 }

	 @Override
	 public void tick(BlockState state, World worldIn, BlockPos pos, Random rand) {
		 isValidPosition(state, worldIn, pos); //Check and see if we can still exist.
		 if (worldIn.getBlockState(pos) == state) //If we can:
		 {
			 if (!worldIn.isAreaLoaded(pos, 1)) //Make sure we should bother checking
				 return;
			 if (worldIn.getLight(pos.up()) >= 9 && checkFertile(worldIn, pos)) //Check for light and water
			 {
				 boolean canGrow = rand.nextInt(3) == 0;
				 if (!isMaxAge(state)) {
					 if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, canGrow)) {
						 worldIn.setBlockState(pos, withAge(getAge(state) + 1));
						 ForgeHooks.onCropsGrowPost(worldIn, pos, state);
						 if (isMaxAge(worldIn.getBlockState(pos)) && getNextState() != null && worldIn.getBlockState(pos = pos.up()).getMaterial().isReplaceable()) {
							 if (ForgeHooks.onCropsGrowPre(worldIn, pos, getNextState(), canGrow)) {
								 worldIn.setBlockState(pos, getNextState());
								 ForgeHooks.onCropsGrowPost(worldIn, pos, getNextState());
							 }
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

	 public boolean checkFertile(World world, BlockPos pos) {
		 return world.getBlockState(pos.down()).isFertile(world, pos.down());
	 }

}
