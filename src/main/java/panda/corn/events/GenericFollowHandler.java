package panda.corn.events;

import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GenericFollowHandler {
	Class<?> animal = null;
	Item item = null;
	
	public GenericFollowHandler(Class<?> animal, Item item) {
		super();
		this.animal = animal;
		this.item = item;
	}
	
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		 if(animal.isInstance(event.getEntity()) && !event.getWorld().isRemote){
			 AnimalEntity theAnimal = (AnimalEntity)event.getEntity();
			 theAnimal.goalSelector.addGoal(8, new TemptGoal(theAnimal, 1.0D, false, Ingredient.fromItems(item)));
		 }
	}
}
