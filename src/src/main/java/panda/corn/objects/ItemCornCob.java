package panda.corn.objects;

import net.minecraft.item.ItemFood;

public class ItemCornCob extends ItemFood{

	public ItemCornCob(int amount,float sat) {
		super(amount,sat, false);
		this.setRegistryName("corncob");
	}

}
