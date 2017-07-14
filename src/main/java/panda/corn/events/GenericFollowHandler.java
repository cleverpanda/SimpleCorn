package panda.corn.events;

import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenericFollowHandler {
	Class animal = null;
	Item item = null;
	
	public GenericFollowHandler(Class animal, Item item) {
		super();
		this.animal = animal;
		this.item = item;
	}
	
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		 if(animal.isInstance(event.getEntity()) && !event.getWorld().isRemote){
			 EntityAnimal theAnimal = (EntityAnimal)event.getEntity();
			 theAnimal.tasks.addTask(8, new EntityAITempt(theAnimal, 1.0D, item, false));
		 }
	}
}
