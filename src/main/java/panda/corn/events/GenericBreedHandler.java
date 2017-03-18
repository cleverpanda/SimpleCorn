package panda.corn.events;

import java.util.Random;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenericBreedHandler {
	//Majority of code found from Minecraftforums user creepytroll69
	Class animal = null;
	Item breedItem = null;
	
	public GenericBreedHandler(Class animal, Item breedItem) {
		super();
		this.animal = animal;
		this.breedItem = breedItem;
	}

	@SubscribeEvent
	public void breed(EntityInteract event)
	{
		if (event.getEntityPlayer().inventory.getCurrentItem() != null && event.getEntityPlayer().inventory.getCurrentItem().getItem() == breedItem)
		{
			if (animal.isInstance(event.getTarget()))
			{
				ItemStack itemstack = event.getEntityPlayer().inventory.getCurrentItem();

				if (((EntityAgeable) event.getTarget()).getGrowingAge() == 0 && !((EntityAnimal)event.getTarget()).isInLove())
				{
					if (!event.getEntityPlayer().capabilities.isCreativeMode)
					{
						itemstack.shrink(1);;

						if (itemstack.isEmpty())
						{
							event.getEntityPlayer().inventory.setInventorySlotContents(event.getEntityPlayer().inventory.currentItem, (ItemStack)null);
						}
					}

					((EntityAnimal)event.getTarget()).setInLove(event.getEntityPlayer());
					Random rand = new Random();

					for (int i = 0; i < 7; ++i)
					{
						double d0 = event.getWorld().rand.nextGaussian() * 0.02D;
						double d1 = event.getWorld().rand.nextGaussian() * 0.02D;
						double d2 = event.getWorld().rand.nextGaussian() * 0.02D;

						if (event.getWorld().isRemote){
							event.getTarget().world.spawnParticle(EnumParticleTypes.HEART, event.getTarget().posX + rand.nextFloat() * event.getTarget().width * 2.0F - event.getTarget().width, event.getTarget().posY + 0.5D + rand.nextFloat() * event.getTarget().height, event.getTarget().posZ + rand.nextFloat() * event.getTarget().width * 2.0F - event.getTarget().width, d0, d1, d2);	
						}
					}
				}
			}
		}
	}
}

