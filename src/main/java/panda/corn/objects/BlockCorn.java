package panda.corn.objects;

import java.util.Random;

import panda.corn.ConfigSimpleCorn;
import panda.corn.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class BlockCorn extends BlockCrops implements IGrowable{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 11);
	
	 private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, .875D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
	
	public BlockCorn()
	{
		setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		setTickRandomly(true);
		setHardness(0.3F);  
		this.setSoundType(SoundType.PLANT);
		this.disableStats();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CROPS_AABB[state.getValue(this.getAgeProperty())];
    }
	
	@Override
	protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }

    @Override
	public int getMaxAge()
    {
        return 11;
    }

    @Override
	protected int getAge(IBlockState state)
    {
        return state.getValue(this.getAgeProperty());
    }

    @Override
	public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), age);
    }

    @Override
	public boolean isMaxAge(IBlockState state)
    {
        return state.getValue(this.getAgeProperty()).intValue() >= this.getMaxAge();
    }
    
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	int age = state.getValue(AGE);
    	//System.out.println(ConfigSimpleCorn.growChance);
    	//states  are on the bottom,have blocks above, or corn is done, do not grow them
    	if( age < 9 && (worldIn.getBlockState(pos.down()).getBlock() == this || canBlockStay(worldIn, pos,state))
    			&& worldIn.getLightFromNeighbors(pos.up()) >= 9) {
    		
    		boolean canGrow = (rand.nextInt(ConfigSimpleCorn.growChance) == 0);
    		
    		if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, canGrow)) {
	    		//Then Corn can grow
	    		if(age < 3){
	    			worldIn.setBlockState(pos, this.getStateFromMeta(age + 1));
	    		}
	    		else
	    			if(age == 3 && worldIn.getBlockState(pos.up()).getMaterial().isReplaceable()){
	    				worldIn.setBlockState(pos, this.getStateFromMeta(4));
	    				worldIn.setBlockState(pos.up(), this.getStateFromMeta(5));
	    			}
	    		if(age == 5){
	    			worldIn.setBlockState(pos, this.getStateFromMeta(6));
	    		}
	    		if(age == 6 && worldIn.getBlockState(pos.up()).getMaterial().isReplaceable()){
	    			worldIn.setBlockState(pos, this.getStateFromMeta(7));
	    			worldIn.setBlockState(pos.up(), this.getStateFromMeta(8));
	    		}
	    		if(age == 8){
	    			worldIn.setBlockState(pos, this.getStateFromMeta(11));
	    			worldIn.setBlockState(pos.down(), this.getStateFromMeta(10));
	    			worldIn.setBlockState(pos.down(2), this.getStateFromMeta(9));
	    		}
	    		net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
    		}
    	}
    }
    
    private void dropcorn(World world, BlockPos pos, IBlockState state){
		ItemStack out;

		int s = state.getValue(AGE);
		//if corn is ripe
		if( s > 9 ){ 
			float chance = world.rand.nextInt(4);
			out = new ItemStack(getCrop(),chance > 2? 2:1); //40% chance to get 2
			Block.spawnAsEntity(world,pos,out);
		}		
	}

	@Override
	protected Item getSeed()
	{
		return ModItems.KERNELS;
	}

	@Override
	protected Item getCrop()
	{
		return ModItems.COB;
	}
	
	@Override
	public boolean isFertile(World world, BlockPos pos)
    {
     return false;
    }

	@Override
	public boolean canBlockStay(World world, BlockPos pos,IBlockState state)
	{
		if (world.getBlockState(pos.down()).getBlock() == this|| world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND  || world.getBlockState(pos.down()).getBlock().isFertile(world, pos))
		{
			return true;
		}
		else{
			return this.canPlaceBlockAt(world, pos);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		int s = state.getValue(AGE);
		 //if corn is ripe
		if(s > 8){
			return this.getCrop();
		}

		return this.getSeed();
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
	{
		return state.getValue(AGE).intValue() < 9 && world.getBlockState(pos.up()).getMaterial().isReplaceable();
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(AGE);
	}

	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE);
    }

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos.down()).getBlock();
		return block.canSustainPlant(world.getBlockState(pos.down()), world, pos, EnumFacing.UP, this) || block == this && world.getBlockState(pos).getMaterial().isReplaceable();
	}  

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) 
	{
		if(world.getBlockState(pos.up()).getBlock() == this){
			dropcorn(world,pos,state);
			world.setBlockToAir(pos.up());
		}
		if(world.getBlockState(pos.up(2)).getBlock() == this){
			dropcorn(world,pos,state);
			world.setBlockToAir(pos.up(2));
		}
		if(world.getBlockState(pos.down()).getBlock() == this){
			dropcorn(world,pos,state);
			world.setBlockToAir(pos.down());
		}
		if(world.getBlockState(pos.down(2)).getBlock() == this){
			dropcorn(world,pos,state);
			world.setBlockToAir(pos.down(2));
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(!ConfigSimpleCorn.useeasyharvesting){
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
		
		int s = state.getValue(AGE);
		 //if corn is ripe
		if(s > 8 ){
			breakBlock(worldIn,pos,state);
				worldIn.setBlockState(pos.down(s-9), this.getDefaultState());
			return true;
		}
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos,IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.updateTick(worldIn, pos, state, rand);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		int j = state.getValue(AGE);

		if(j > 8){
			entityIn.motionX *= 0.1D;
			entityIn.motionZ *= 0.1D;  
		}
	}

}
