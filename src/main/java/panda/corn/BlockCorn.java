package panda.corn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockFarmland;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCorn extends BlockBush implements IGrowable{ //implements IPlantable{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 11);
	private Block previousblock;
	public BlockCorn()
    {
		setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		float f = 0.375F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        setTickRandomly(true);
        setHardness(0.3F);  
        this.setStepSound(soundTypeGrass);
        this.disableStats();
    }
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
		int j = ((Integer)state.getValue(AGE)).intValue();
		
		if( j != 9 && j!= 10 && j!= 11){ //states  are on the bottom,have blocks above, or corn is done, do not grow them
			if (worldIn.getBlockState(pos.down()).getBlock() == this || canBlockStay(worldIn, pos,state))
			{
				if (worldIn.isAirBlock(pos.up()) || j == 4 || j == 7)
				{
                	if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
                    {
                		if (rand.nextInt(2) == 1)
                        {
                			
                				//Then Corn can grow
                				if(j == 0 || j == 1 || j == 2){
                					worldIn.setBlockState(pos, this.getStateFromMeta(j+1));
                				}
                				else
                				if(j == 3){
                					worldIn.setBlockState(pos, this.getStateFromMeta(4));
                					worldIn.setBlockState(pos.up(), this.getStateFromMeta(5));
                				}
                				if(j == 5){
                					worldIn.setBlockState(pos, this.getStateFromMeta(6));
                				}
                				if(j == 6){
                					worldIn.setBlockState(pos, this.getStateFromMeta(7));
                					worldIn.setBlockState(pos.up(), this.getStateFromMeta(8));
                				}
                				if(j == 8){
                					worldIn.setBlockState(pos, this.getStateFromMeta(11));
                					worldIn.setBlockState(pos.down(), this.getStateFromMeta(10));
                					worldIn.setBlockState(pos.down(2), this.getStateFromMeta(9));
                				}
                				if(j == 4 || j == 7){
                					if(worldIn.getBlockState(pos.up()).getBlock() == this){
                						if (rand.nextInt(6) == 0)
                                        {
                						int k = ((Integer) worldIn.getBlockState(pos.up()).getValue(AGE)).intValue();
                						if(k == 5){
                        					worldIn.setBlockState(pos.up(), this.getStateFromMeta(6));
                        				}
                        				if(k == 6){
                        					worldIn.setBlockState(pos.up(), this.getStateFromMeta(7));
                        					worldIn.setBlockState(pos.up(2), this.getStateFromMeta(8));
                        				}
                        				if(k== 7){
                        					worldIn.setBlockState(pos.up(2), this.getStateFromMeta(11));
                        					worldIn.setBlockState(pos.up(), this.getStateFromMeta(10));
                        					worldIn.setBlockState(pos, this.getStateFromMeta(9));
                        				}
                        				if(k == 8){
                        					worldIn.setBlockState(pos.up(), this.getStateFromMeta(11));
                        					worldIn.setBlockState(pos, this.getStateFromMeta(10));
                        					worldIn.setBlockState(pos.down(), this.getStateFromMeta(9));
                        				}
                                        }//((BlockCorn) worldIn.getBlockState(pos.up()).getBlock()).updateTick(worldIn, pos,state,rand);
                					}
                				}
                        }
                    }
            	}
        	}
		}
    }
	
	protected Item getSeed()
    {
        return GameRegistry.findItem(Corn.MODID, "kernels");
    }
	
	protected Item getCrop()
    {
        return GameRegistry.findItem(Corn.MODID, "corncob");
    }
	
	@Override
	public boolean canBlockStay(World world, BlockPos pos,IBlockState state)
    {
    	if (world.getBlockState(pos.down()).getBlock() == this || world.getBlockState(pos.down()).getBlock()==Blocks.farmland)
        {
            return true;
        }
    	else{
        	return this.canPlaceBlockAt(world, pos);
        }
    }
	
	protected boolean canPlaceBlockOn(Block ground)
    {
		Block block = net.minecraft.block.BlockFarmland.getBlockFromName("farmland").getStateFromMeta(7).getBlock();
		return ground == block;
    }
    
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		int s = ((Integer)state.getValue(AGE)).intValue();
		if(s == 9 || s == 10 || s == 11 ){ //if corn is ripe
			return this.getCrop();
    	}
		
		return this.getSeed();
    }
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return ((Integer)state.getValue(AGE)).intValue() < 9;
    }
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(AGE)).intValue();
    }
	@Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {AGE});
    }
      
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
		Block block = world.getBlockState(pos.down()).getBlock();
        if (block.canSustainPlant(world, pos, EnumFacing.UP, this)){
        	return true;
        }

        if (block == this)
        {
            return true;
        }

		return false;
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
	
	private void dropcorn(World world, BlockPos pos, IBlockState state){
		ItemStack out = null;
    	int s = ((Integer)state.getValue(AGE)).intValue();
    	if( s == 10 || s == 11 ){ //if corn is ripe
    		float chance = world.rand.nextFloat();
    		if( chance >= .40){
    			out = new ItemStack(GameRegistry.findItem(Corn.MODID, "corncob"),2);
    		}else if( chance >=.1){
    			out = new ItemStack(GameRegistry.findItem(Corn.MODID, "corncob"));
    		}
    		
    	}
    	else{
    		out = new ItemStack(GameRegistry.findItem(Corn.MODID, "kernels"));
    	}
    	super.spawnAsEntity(world,pos,out);
	}
	

    public final boolean checkForDrop(World world, BlockPos pos, IBlockState state)
    {
        if (this.canBlockStay(world, pos,state) )
        {
        	return true;
        }
        else
        {
        	ItemStack out;
        	int s = ((Integer)state.getValue(AGE)).intValue();
        	if(s == 9 || s == 10 || s == 11 ){ //if corn is ripe
        		if(world.rand.nextFloat() >= .40){
        			out = new ItemStack(GameRegistry.findItem(Corn.MODID, "corncob"),1);
        		}else{
        			out = new ItemStack(GameRegistry.findItem(Corn.MODID, "corncob"));
        		}
        		
        	}else{
        		out = new ItemStack(GameRegistry.findItem(Corn.MODID, "kernels"));
        	}
        	
        	super.spawnAsEntity(world,pos,out);
            world.setBlockToAir(pos);
           
        }
        return false;
    }

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos,IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.updateTick(worldIn, pos, state, rand);
	}
	
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
		int j = ((Integer)state.getValue(AGE)).intValue();

		if(j > 8){
			entityIn.motionX *= 0.1D;
			entityIn.motionZ *= 0.1D;  
		}
    }
}
    

    
	
	
	/*
	@SideOnly(Side.CLIENT)@Override
	public Item getItem(World worldIn, BlockPos pos)
	{
		int s = ((Integer)worldIn.getBlockState(pos).getValue(AGE)).intValue();
		if(s == 9 || s == 10 || s == 11 ){ //if corn is ripe
			return GameRegistry.findItem(Corn.MODID, "corncob");
    	}
		return GameRegistry.findItem(Corn.MODID, "kernels");
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world,BlockPos pos, IBlockState state, int fortune){
		List<ItemStack> out = new ArrayList<ItemStack>();
		if(((World)world).rand.nextFloat() >= .50){
			out.add(new ItemStack(GameRegistry.findItem(Corn.MODID, "corncob"),2));
			return out;
		}
		out.add(new ItemStack(GameRegistry.findItem(Corn.MODID, "corncob")));
		return out;
	}

	@SideOnly(Side.CLIENT)@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
	{
	    return worldIn.getBiomeGenForCoords(pos).getGrassColorAtPos(pos);
	}
	
	


	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}
	
	

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		// TODO Auto-generated method stub
		IBlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return getDefaultState();
        return state;
		
		//return this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0));
	}*/

