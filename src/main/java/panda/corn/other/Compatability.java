package panda.corn.other;

import panda.corn.registry.ObjectList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;

import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.crafting.FermenterRecipe;
import blusunrize.immersiveengineering.api.crafting.SqueezerRecipe;
import blusunrize.immersiveengineering.api.tool.BelljarHandler;

public class Compatability {
	
	private Compatability() {}
	
	public static void immersiveEngineering(){
		FermenterRecipe.addRecipe(new FluidStack(FluidRegistry.getFluid("ethanol"),Config.ethanolvolume), ItemStack.EMPTY, ObjectList.COB, 6400);
		SqueezerRecipe.addRecipe(new FluidStack(FluidRegistry.getFluid("plantoil"), Config.plantoilvolume), ItemStack.EMPTY, ObjectList.KERNELS, 6400);

		Block blockCrop = ObjectList.CORN;
		BelljarHandler.DefaultPlantHandler cornBelljarHandler = new BelljarHandler.DefaultPlantHandler()
		{
			private HashSet<ComparableItemStack> validSeeds = new HashSet<>();
			@Override
			protected HashSet<ComparableItemStack> getSeedSet()
			{
				return validSeeds;
			}
			@Override
			@SideOnly(Side.CLIENT)
			public IBlockState[] getRenderedPlant(ItemStack seed, ItemStack soil, float growth, TileEntity tile)
			{

				int age = Math.min(6, Math.round(growth*6));

				if(age==4){
					return new IBlockState[]{blockCrop.getStateFromMeta(age),blockCrop.getStateFromMeta(age+1)};
				}
				if(age==5){
					return new IBlockState[]{blockCrop.getStateFromMeta(4),blockCrop.getStateFromMeta(6)};
				}
				if(age==6){
					return new IBlockState[]{blockCrop.getStateFromMeta(9),blockCrop.getStateFromMeta(11)};
				}
				return new IBlockState[]{blockCrop.getStateFromMeta(age)};
			}
			@Override
			@SideOnly(Side.CLIENT)
			public float getRenderSize(ItemStack seed, ItemStack soil, float growth, TileEntity tile)
			{
				return .875f;
			}
		};
		BelljarHandler.registerHandler(cornBelljarHandler);
		cornBelljarHandler.register(new ItemStack(ObjectList.KERNELS), new ItemStack[]{new ItemStack(ObjectList.COB,Config.clochedropamount),new ItemStack(ObjectList.KERNELS)},new ItemStack(Blocks.DIRT), blockCrop.getDefaultState());
	}

}
























