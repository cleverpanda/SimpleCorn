package panda.corn;

import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PigFollowHandler {
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		 if(event.getEntity() instanceof EntityPig && !event.getWorld().isRemote){
			 EntityPig pig = (EntityPig)event.getEntity();
			 pig.tasks.addTask(8, new EntityAITempt(pig, 1.0D, GameRegistry.findItem(Corn.MODID, "corncob"), false));
		 }
	}
}
