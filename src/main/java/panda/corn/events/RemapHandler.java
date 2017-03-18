package panda.corn.events;

import java.util.List;

import panda.corn.registry.ObjectList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RemapHandler {

	public static void processingMissingMap(FMLMissingMappingsEvent event)
    {
        List<MissingMapping> missingMappings = event.get();
        for (MissingMapping map : missingMappings)
        {
        	if(map.name.equals("corn:corn")){
        		map.remap(ObjectList.CORN);
        	}else
        		if(map.name.equals("corn:corncob")){
        			map.remap(ObjectList.COB);
        		}else
        			if(map.name.equals("corn:kernels")){
        				map.remap(ObjectList.KERNELS);
        			}else
        				if(map.name.equals("corn:poppedcorn")){
        					map.remap(ObjectList.POPCORN);
        				}else
        					if(map.name.equals("corn:roastedcornn")){
        						map.remap(ObjectList.ROASTED_CORN);
        					}else
        						if(map.name.equals("corn:popfirework")){
        							map.remap(ObjectList.POP_FIREWORK);
        						}else
        							if(map.name.equals("corn:chickencornchowder")){
        								map.remap(ObjectList.CHICKEN_CHOWDER);
        							}else
        								if(map.name.equals("corn:cornchowder")){
        									map.remap(ObjectList.CHOWDER);
        								}


        }
    }
}
