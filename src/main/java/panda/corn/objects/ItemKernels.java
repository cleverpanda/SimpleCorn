package panda.corn.objects;

import panda.corn.registry.ObjectList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

public class ItemKernels extends ItemSeeds{

    public ItemKernels() {
    	super(ObjectList.CORN, Blocks.FARMLAND);
    	this.setRegistryName("kernels");
    }
}
   
