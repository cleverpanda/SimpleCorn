package panda.corn.events;

import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenericFollowHandler {
	Class animal = null;
	Item item = null;
	
	public GenericFollowHandler(Class animal, Item Item) {
		super();
		this.animal = animal;
		this.item = Item;
	}
	
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		 if(animal.isInstance(event.getEntity()) && !event.getWorld().isRemote){
			 EntityAnimal animal = (EntityAnimal)event.getEntity();
			 animal.tasks.addTask(8, new EntityAITempt(animal, 1.0D, item, false));
		 }
	}
}
