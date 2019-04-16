package panda.corn.objects;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import panda.corn.init.ModBlocks;

public class ItemKernels extends ItemSeeds implements IPlantable{

	public ItemKernels() {
		super(ModBlocks.CORN, Blocks.FARMLAND);
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos){
		return EnumPlantType.Crop;
	}
	
	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos){
		return ModBlocks.CORN.getDefaultState();
	}
}
