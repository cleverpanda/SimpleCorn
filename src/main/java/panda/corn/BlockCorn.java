package panda.corn;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCorn extends BlockBush implements IGrowable{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 11);
	 private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, .875D, 1.0D),
		 new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
	private Block previousblock;
	//Items spawn too big
	public BlockCorn()
	{
		setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		//this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
		setTickRandomly(true);
		setHardness(0.3F);  
		this.setSoundType(SoundType.PLANT);
		this.disableStats();
		this.setCreativeTab((CreativeTabs)null);
	}
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CROPS_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }
	
	protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }

    public int getMaxAge()
    {
        return 11;
    }

    protected int getAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }

    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
    }

    public boolean isMaxAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue() >= this.getMaxAge();
    }
    
    

    @Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		int j = ((Integer)state.getValue(AGE)).intValue();
	//states  are on the bottom,have blocks above, or corn is done, do not grow them
		if( j != 9 && j!= 10 && j!= 11){
			if (worldIn.getBlockState(pos.down()).getBlock() == this || canBlockStay(worldIn, pos,state))
			{
				if (worldIn.isAirBlock(pos.up()) || j == 4 || j == 7)
				{
					if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
					{
						if (rand.nextInt(Corn.growChance) == 1)
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
									if (rand.nextInt(Corn.growChance*2) == 0)
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
									}
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
	public boolean isFertile(World world, BlockPos pos)
    {
     return false;
    }

	@Override
	public boolean canBlockStay(World world, BlockPos pos,IBlockState state)
	{
		//
		if (world.getBlockState(pos.down()).getBlock() == this|| world.getBlockState(pos.down()).getBlock()==Blocks.FARMLAND  || world.getBlockState(pos.down()).getBlock().isFertile(world, pos))
		{
			return true;
		}
		else{
			return this.canPlaceBlockAt(world, pos);
		}
	}

	/*@Override
	protected boolean canPlaceBlockOn(Block ground)
	{
		Block block = Block.getBlockFromName("farmland").getStateFromMeta(7).getBlock();
		return ground == block;
	}*/

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		int s = ((Integer)state.getValue(AGE)).intValue();
		 //if corn is ripe
		if(s == 9 || s == 10 || s == 11 ){
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
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos.down()).getBlock();
		if (block.canSustainPlant(world.getBlockState(pos.down()), world, pos, EnumFacing.UP, this) || block == this){
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
		ItemStack out = new ItemStack(GameRegistry.findItem(Corn.MODID, "kernels"));

		int s = ((Integer)state.getValue(AGE)).intValue();
		//if corn is ripe
		if( s == 10 || s == 11 ){ 
			float chance = world.rand.nextFloat();
			if( chance >= .40){
				out = new ItemStack(GameRegistry.findItem(Corn.MODID, "corncob"),2);
			}else if( chance >=.1){
				out = new ItemStack(GameRegistry.findItem(Corn.MODID, "corncob"));
			}
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

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		int j = ((Integer)state.getValue(AGE)).intValue();

		if(j > 8){
			entityIn.motionX *= 0.1D;
			entityIn.motionZ *= 0.1D;  
		}
	}

}
