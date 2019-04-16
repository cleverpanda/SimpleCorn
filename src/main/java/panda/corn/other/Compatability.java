package panda.corn.other;

import panda.corn.ConfigSimpleCorn;
import panda.corn.init.ModBlocks;
import panda.corn.init.ModItems;
import panda.corn.objects.BlockCorn;
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
		FermenterRecipe.addRecipe(new FluidStack(FluidRegistry.getFluid("ethanol"),ConfigSimpleCorn.ethanolvolume), ItemStack.EMPTY, ModItems.CORNCOB, 6400);
		SqueezerRecipe.addRecipe(new FluidStack(FluidRegistry.getFluid("plantoil"), ConfigSimpleCorn.plantoilvolume), ItemStack.EMPTY, ModItems.KERNELS, 6400);

		BelljarHandler.DefaultPlantHandler cornBelljarHandler = new BelljarHandler.DefaultPlantHandler(){
			private HashSet<ComparableItemStack> validSeeds = new HashSet<>();
			@Override
			protected HashSet<ComparableItemStack> getSeedSet(){
				return validSeeds;
			}
			@Override
			@SideOnly(Side.CLIENT)
			public IBlockState[] getRenderedPlant(ItemStack seed, ItemStack soil, float growth, TileEntity tile){
				int age = Math.min(6, Math.round(growth*6));

				if(age == 4){
					return new IBlockState[]{getState(age),getState(age+1)};
				}
				if(age == 5){
					return new IBlockState[]{getState(4),getState(6)};
				}
				if(age == 6){
					return new IBlockState[]{getState(9),getState(11)};
				}
				return new IBlockState[]{getState(age)};
			}
			@Override
			@SideOnly(Side.CLIENT)
			public float getRenderSize(ItemStack seed, ItemStack soil, float growth, TileEntity tile){
				return 0.875f;
			}
		};
		BelljarHandler.registerHandler(cornBelljarHandler);
		cornBelljarHandler.register(new ItemStack(ModItems.KERNELS), new ItemStack[]{new ItemStack(ModItems.CORNCOB,ConfigSimpleCorn.clochedropamount)},new ItemStack(Blocks.DIRT), ModBlocks.CORN.getDefaultState());
	}
	
	private static IBlockState getState(int age){
		IBlockState blockCrop = ModBlocks.CORN.getDefaultState();
		return blockCrop.withProperty(BlockCorn.AGE, age);
	}

}
