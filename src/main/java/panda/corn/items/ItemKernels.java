package panda.corn.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import panda.corn.init.ModBlocks;

public class ItemKernels extends BlockNamedItem implements IPlantable{
	public ItemKernels() {
		super(ModBlocks.CORN, new Item.Properties().group(ItemGroup.MATERIALS));
	}
	
	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.Crop;
    }

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return ModBlocks.CORN.getDefaultState();
	}
}
