package panda.corn.other;

import panda.corn.registry.ObjectList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Recipes {
	public static void register() {
		GameRegistry.addShapelessRecipe(new ItemStack(ObjectList.CHOWDER),
				new Object[] {
			new ItemStack(Items.BOWL, 1),
			new ItemStack(Items.MILK_BUCKET, 1),
			new ItemStack(ObjectList.KERNELS, 1),
			new ItemStack(ObjectList.KERNELS, 1)
		});

		GameRegistry.addShapelessRecipe(new ItemStack(ObjectList.CHICKEN_CHOWDER),
				new Object[] {
			new ItemStack(Items.BOWL, 1),
			new ItemStack(Items.MILK_BUCKET, 1),
			new ItemStack(Items.COOKED_CHICKEN, 1),
			new ItemStack(ObjectList.KERNELS, 1),
			new ItemStack(ObjectList.KERNELS, 1)
		});

		GameRegistry.addShapelessRecipe(new ItemStack(ObjectList.CHICKEN_CHOWDER),
				new Object[] {
			new ItemStack(ObjectList.CHOWDER, 1),
			new ItemStack(Items.COOKED_CHICKEN, 1)
		});

		GameRegistry.addShapelessRecipe(new ItemStack(ObjectList.KERNELS,Config.numkernels),
				new Object[] {
			new ItemStack(ObjectList.COB)
		});

		GameRegistry.addSmelting(ObjectList.COB, new ItemStack(ObjectList.ROASTED_CORN), .2F);
		GameRegistry.addSmelting(ObjectList.KERNELS, new ItemStack(ObjectList.POPCORN,2), .01F);
		MinecraftForge.addGrassSeed(new ItemStack(ObjectList.KERNELS), Config.kernelWeight);
	}
}