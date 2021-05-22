package panda.corn.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import panda.corn.init.ModBlocks;

public class ItemKernels extends BlockNamedItem implements IPlantable{
	public ItemKernels() {
		super(ModBlocks.CORN, new Properties().group(ItemGroup.MATERIALS));
	}
	
	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.CROP;
    }

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return ModBlocks.CORN.getDefaultState();
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if(this.isInGroup(group))
			items.add(new ItemStack(this));
	}
}
