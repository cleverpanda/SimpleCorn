package panda.corn.objects;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemPopcorn extends ItemFood{

	public ItemPopcorn(int amount, float saturation) {
		super(amount, saturation, false);
		this.setRegistryName("poppedcorn");
	}
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 8;
    }

}
