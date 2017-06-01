package panda.corn.objects;

import net.minecraft.item.ItemFood;

public class ItemRoastedCorn extends ItemFood{
	public ItemRoastedCorn(int amount,float sat) {
		super(amount,sat, false);
		this.setRegistryName("roastedcorn");
	}
}
