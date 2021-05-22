package panda.corn.blocks;

import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import panda.corn.config.ConfigSimpleCorn;
import panda.corn.init.ModBlocks;
import panda.corn.init.ModItems;

public class BlockCorn extends CropsBlock implements IGrowable{

	public static final IntegerProperty CORNAGE = BlockStateProperties.AGE_0_5;

	 protected static final VoxelShape[] SHAPES = new VoxelShape[]{
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
		Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)
	};
	   
	 public BlockCorn(){
		 super(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.3F,0F).sound(SoundType.CROP));
	 }

	 @Override
	 public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
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

	 
//	 protected static float getGrowthChance(Block blockIn, IBlockReader worldIn, BlockPos pos) {
//		 return this.isMaxAge(state) ? 0.2f : 0.0f;
//	 }

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
		 return canGrow(worldIn, pos, worldIn.getBlockState(pos), worldIn.isRemote);
	 }

	 @Override
	 protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder){
		 builder.add(CORNAGE);
	 }

	 @Override
	 protected int getBonemealAgeIncrease(World worldIn) {
		 return 1;
	 }

	 @Override
	 public int getMaxAge() {
		 return 4;
	 }

	 @Override
	   @OnlyIn(Dist.CLIENT)
	   public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
	      return new ItemStack(ModItems.KERNELS);
	   }

	 @Override
	 public void grow(World worldIn, BlockPos pos, BlockState state) {
		 isValidPosition(state, worldIn, pos); //Check and see if we can still exist.
		 
		 if (worldIn.getBlockState(pos) == state) //If we can:
		 {
			 
			 if (!worldIn.isAreaLoaded(pos, 1)) { //Make sure we should bother checking
				 return;
			 }
			 
			 if (worldIn.getLightSubtracted(pos, 0) >= 9 && checkFertile(worldIn, pos)) //Check for light and water
			 {
				 boolean canGrow = worldIn.rand.nextInt(2) == 0;

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
	 
		@SuppressWarnings("deprecation")
		@Override
		public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
			
			if(ConfigSimpleCorn.useeasyharvesting){
				 if(state.get(this.getAgeProperty()) == 5){
					 worldIn.removeBlock(pos, false);
					 if(worldIn.setBlockState(pos, this.getDefaultState())) {
						 return ActionResultType.PASS;
					 }
					 return ActionResultType.FAIL;
				 }
			 }
			
			return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}

	 public BlockState getNextState() {
		 return ModBlocks.CORN_MID.getDefaultState();
	 }
	 
	 
	 @Override
	 public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		 isValidPosition(state, worldIn, pos); //Check and see if we can still exist.
		 
		 if (worldIn.getBlockState(pos) == state) //If we can:
		 {
			 
			 if (!worldIn.isAreaLoaded(pos, 1)) { //Make sure we should bother checking
				 return;
			 }
			 
			 if (worldIn.getLightSubtracted(pos, 0) >= 9 && checkFertile(worldIn, pos)) //Check for light and water
			 {
				 boolean canGrow = random.nextInt(10) == 0;

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

	 @Override
	 public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player){
		 return new ItemStack(ModItems.KERNELS);
	 }
	 
	@Override
	public boolean ticksRandomly(BlockState state) {
		return state.get(this.getAgeProperty()) < 5;
	}
}
