package panda.corn.events;

import java.util.Random;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class GenericBreedHandler {
	//Majority of code found from Minecraftforums user creepytroll69
	Class<?> animal = null;
	Item breedItem = null;

	public GenericBreedHandler(Class<?> animal, Item breedItem) {
		super();
		this.animal = animal;
		this.breedItem = breedItem;
	}

	@SubscribeEvent
	public void breed(EntityInteract event)
	{
		if (event.getEntityPlayer().inventory.getCurrentItem() != null && event.getEntityPlayer().inventory.getCurrentItem() != ItemStack.EMPTY && event.getEntityPlayer().inventory.getCurrentItem().getItem() == breedItem && animal.isInstance(event.getTarget()))
		{

			ItemStack itemstack = event.getEntityPlayer().inventory.getCurrentItem();

			if (((AgeableEntity) event.getTarget()).getGrowingAge() == 0 && !((AnimalEntity)event.getTarget()).isInLove())
			{
				if (!event.getEntityPlayer().isCreative())
				{
					itemstack.shrink(1);

					if (itemstack.isEmpty())
					{
						event.getEntityPlayer().inventory.setInventorySlotContents(event.getEntityPlayer().inventory.currentItem, ItemStack.EMPTY);
					}
				}

				((AnimalEntity)event.getTarget()).setInLove(event.getEntityPlayer());
				Random rand = new Random();

				for (int i = 0; i < 7; ++i)
				{
					double d0 = event.getWorld().rand.nextGaussian() * 0.02D;
					double d1 = event.getWorld().rand.nextGaussian() * 0.02D;
					double d2 = event.getWorld().rand.nextGaussian() * 0.02D;

					if (event.getWorld().isRemote){
						event.getTarget().world.addParticle(ParticleTypes.HEART, event.getTarget().posX + rand.nextFloat()
						* event.getTarget().getWidth() * 2.0F - event.getTarget().getWidth(), event.getTarget().posY + 0.5D
						+ rand.nextFloat() * event.getTarget().getHeight(), event.getTarget().posZ + rand.nextFloat()
						* event.getTarget().getWidth() * 2.0F - event.getTarget().getWidth(), d0, d1, d2);	
					}
				}
			}
		}
	}
}

