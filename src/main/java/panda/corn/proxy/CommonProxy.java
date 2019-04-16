package panda.corn.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.oredict.OreDictionary;
import panda.corn.init.ModItems;

public class CommonProxy {

    public static void registerModels(ModelRegistryEvent event) {}

    public void registerOreDicts(){
    	OreDictionary.registerOre("seedsCorn", ModItems.KERNELS);
    	OreDictionary.registerOre("seedCorn", ModItems.KERNELS);
    	OreDictionary.registerOre("listAllVeggies", ModItems.COB);	
    	OreDictionary.registerOre("cropCorn", ModItems.COB);	
    	OreDictionary.registerOre("listAllVeggies", ModItems.ROASTED_CORN);
    	OreDictionary.registerOre("listAllseed", ModItems.KERNELS);
    }
}

