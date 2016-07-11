package panda.corn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCornChowder extends ItemFood{

	public ItemCornChowder(int healAmount,float saturation) {
		super(healAmount,saturation,false);
	    this.setMaxStackSize(1);
		// TODO Auto-generated constructor stub
	}
    

/**
 * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
 * the Item before the action is complete.
 */
public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
{
    super.onItemUseFinish(stack, worldIn, playerIn);
    return new ItemStack(Items.bowl);
}

}
