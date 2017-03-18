package panda.corn.other;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import panda.corn.registry.ObjectList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;



public class Compatability {

	//TODO SHAME. DO THIS BETTER.
	public static void IE2() throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		Class<?> Fermenter = null;
		Fermenter = Class.forName("blusunrize.immersiveengineering.api.crafting.FermenterRecipe");


		Method methodToFind = null;
		methodToFind = Fermenter.getMethod("addRecipe", new Class[] { FluidStack.class,ItemStack.class,Object.class,int.class });
		if(methodToFind == null) {

		} else {

			methodToFind.invoke(Fermenter,new FluidStack(FluidRegistry.getFluid("ethanol"),Config.ethanolvolume), null, ObjectList.COB, 6400);
		}

	}
}
