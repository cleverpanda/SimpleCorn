package panda.corn.other;

import cofh.thermalexpansion.util.managers.machine.InsolatorManager;
import cofh.thermalfoundation.item.ItemFertilizer;
import net.minecraft.item.ItemStack;
import panda.corn.init.ModItems;

public class ThermalCompat {
	
	private ThermalCompat() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static void init(){
		InsolatorManager.addRecipe(4800, 1200, new ItemStack(ModItems.KERNELS), ItemFertilizer.fertilizerBasic, new ItemStack(ModItems.CORNCOB,2));
		InsolatorManager.addRecipe(7200, 1800, new ItemStack(ModItems.KERNELS), ItemFertilizer.fertilizerRich, new ItemStack(ModItems.CORNCOB,4));
		InsolatorManager.addRecipe(9600, 2400, new ItemStack(ModItems.KERNELS), ItemFertilizer.fertilizerFlux, new ItemStack(ModItems.CORNCOB,6));
	}
}
