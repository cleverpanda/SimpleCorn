package panda.corn.objects;

import java.util.Arrays;
import java.util.List;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;

public class BlockCorn extends BlockCrops implements IGrowable{
	public static final int MAXAGE = 11;
	public static final PropertyInteger CORNAGE = PropertyInteger.create("age", 0, MAXAGE);
	
	 private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, .875D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
	
	public BlockCorn()
	{
		setDefaultState(this.blockState.getBaseState().withProperty(CORNAGE, 0));
		setTickRandomly(true);
		setHardness(0.3F);  
		this.setSoundType(SoundType.PLANT);
		this.disableStats();
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.checkForDrop(worldIn, pos, state);
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.notifyNeighborsOfStateChange(pos.up(), this, true);
    }
	
    protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.canBlockStay(worldIn, pos, state))
        {
            return true;
        }
        else
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
    }

	protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos)
    {
		return 1/((float)ConfigSimpleCorn.growChance + 1);
    }
	
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        super.getDrops(drops, world, pos, state, 0);
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();

        if (age > 9) //Top two ripe blocks only
        {
            for (int i = 0; i < 2 + fortune; ++i)//79% ^(2 rolls) = 80% of getting 2, 20% chance to get 1
            {
                if (rand.nextInt(8) <= 7) ///79%
                {
                    drops.add(new ItemStack(this.getCrop()));
                }
            }
        }else{
        	drops.add(new ItemStack(this.getSeed()));
        }
    }
	
	@Override
	public boolean canBlockStay(World world, BlockPos pos,IBlockState state)
	{
		List<Integer> upperBlocks = Arrays.asList(8,11);
		List<Integer> middleBlocks = Arrays.asList(5,6,7,10);
		List<Integer> lowerBlocks = Arrays.asList(1,2,3,4,9);
		
		IBlockState belowMe = world.getBlockState(pos.down());
		IBlockState me = world.getBlockState(pos);
		IBlockState aboveMe = world.getBlockState(pos.up());
		int myAge = me.getValue(getAgeProperty());
		//We are a lower block
		if( lowerBlocks.contains(myAge) ){
			//Below us must be fertile soil AND above us must be a higher state OR we are lower than 4
			if( ( belowMe.getBlock() == Blocks.FARMLAND || belowMe.getBlock().isFertile(world, pos.down()) ) && ( myAge < 4 || aboveMe.getBlock() == this && aboveMe.getValue(getAgeProperty()) > myAge)){
				return true;
			}
		}else
		//We are a middle block
		if( middleBlocks.contains( myAge ) ){
			//Below me must be a lower block, AND above me must be a top block
			if(aboveMe.getBlock() == this && belowMe.getBlock() == this && lowerBlocks.contains(belowMe.getValue(getAgeProperty())) && upperBlocks.contains(aboveMe.getValue(getAgeProperty())) ){
				return true;
			}
		}else
		//We are an upper block
		if( upperBlocks.contains( myAge ) ){
			//Below me must be a middle block
			if(belowMe.getBlock() == this && lowerBlocks.contains(belowMe.getValue(getAgeProperty()))){
				return true;
			}
		}

		return this.canPlaceBlockAt(world, pos);
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
	{
		return state.getValue(CORNAGE).intValue() < 9 && world.getBlockState(pos.up()).getMaterial().isReplaceable();
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos.down()).getBlock();
		return block.canSustainPlant(world.getBlockState(pos.down()), world, pos, EnumFacing.UP, this) || block == this && world.getBlockState(pos).getMaterial().isReplaceable();
	}
    
    @Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos,IBlockState state) {
		return true;
	}
    
    @Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, CORNAGE);
    }
	
	@Override
	protected PropertyInteger getAgeProperty()
    {
        return CORNAGE;
    }
	
	@Override
    protected int getBonemealAgeIncrease(World worldIn)
    {
        return MathHelper.getInt(worldIn.rand, 1, 2);
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CROPS_AABB[state.getValue(CORNAGE)];
    }
	
	@Override
    protected Item getCrop()
    {
        return ModItems.COB;
    }

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(CORNAGE).intValue() >= 9 ? this.getCrop() : this.getSeed();
    }

	@Override
    public int getMaxAge()
    {
        return MAXAGE;
    }

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(CORNAGE);
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}

	@Override
    protected Item getSeed()
    {
        return ModItems.KERNELS;
    }

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.withAge(meta);
	}

	//TODO how is the different from update tick?
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.updateTick(worldIn, pos, state, rand);
	}  

	@Override
	public boolean isFertile(World world, BlockPos pos)
    {
     return false;
    }

	@Override
	public boolean isMaxAge(IBlockState state)
    {
        return state.getValue(CORNAGE).intValue() >= MAXAGE;
    }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(!ConfigSimpleCorn.useeasyharvesting){
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
		
		int s = state.getValue(CORNAGE);
		 //if corn is ripe
		if(s >= 9  ){
			breakBlock(worldIn,pos,state);
				worldIn.setBlockState(pos.down(s-9), this.getDefaultState());
			return true;
		}
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		int j = state.getValue(CORNAGE);

		if(j > 8){
			entityIn.motionX *= 0.1D;
			entityIn.motionZ *= 0.1D;  
		}
	}

	//TODO
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
		if(state.getBlock() != this){
			return;
		}
		
    	int age = state.getValue(getAgeProperty());
    	//states  are on the bottom,have blocks above, or corn is done, do not grow them
    	if( age < 9 && (worldIn.getBlockState(pos.down()).getBlock() == this || canBlockStay(worldIn, pos,state))
    			&& worldIn.getLightFromNeighbors(pos.up()) >= 9) {
    		
    		boolean canGrow = (rand.nextInt(ConfigSimpleCorn.growChance) == 0);
    		
    		if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, canGrow)) {
	    		//Then Corn can grow
	    		if(age < 3){
	    			worldIn.setBlockState(pos, this.withAge(age + 1));
	    			worldIn.setBlockState(pos.up(), this.withAge(5));
	    		}else
	    		if(age == 3 && canPlaceBlockAt(worldIn, pos.up())){
	    			worldIn.setBlockState(pos, this.withAge(4));
	    			worldIn.setBlockState(pos.up(), this.withAge(5));
	    		}else
	    		if(age == 5){
	    			worldIn.setBlockState(pos, this.withAge(6));
	    		}else
	    		if(age == 6 && canPlaceBlockAt(worldIn, pos.up())){
	    			worldIn.setBlockState(pos, this.withAge(7));
	    			worldIn.setBlockState(pos.up(), this.withAge(8));
	    		}else
	    		if(age == 8){
	    			worldIn.setBlockState(pos, this.withAge(11));
	    			worldIn.setBlockState(pos.down(), this.withAge(10));
	    			worldIn.setBlockState(pos.down(2), this.withAge(9));
	    		}
	    		ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
    		}
    	}
    }

}
