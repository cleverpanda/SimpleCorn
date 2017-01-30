package panda.corn;

import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChickenFollowHandler {

	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		 if(event.getEntity() instanceof EntityChicken && !event.getWorld().isRemote){
			 EntityChicken chicken = (EntityChicken)event.getEntity();
			 chicken.tasks.addTask(8, new EntityAITempt(chicken, 1.0D, Corn.kernels, false));
		 }
	}
}
