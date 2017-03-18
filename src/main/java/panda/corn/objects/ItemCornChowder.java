package panda.corn.objects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCornChowder extends ItemFood{

	public ItemCornChowder(int healAmount,float saturation) {
		super(healAmount,saturation,false);
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn)
	{
		super.onItemUseFinish(stack, worldIn, playerIn);
		return new ItemStack(Items.BOWL);
	}

}
