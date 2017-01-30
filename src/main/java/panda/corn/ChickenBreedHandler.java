package panda.corn;

import java.util.Random;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChickenBreedHandler {
	//Majority of code found from Minecraftforums user creepytroll69
	@SubscribeEvent
	public void breed(EntityInteract event)
	{
		if (event.getEntityPlayer().inventory.getCurrentItem() != null && event.getEntityPlayer().inventory.getCurrentItem().getItem() == Corn.kernels)
		{
			if (event.getTarget() instanceof EntityChicken)
			{
				ItemStack itemstack = event.getEntityPlayer().inventory.getCurrentItem();

				if (((EntityAgeable) event.getTarget()).getGrowingAge() == 0 && !((EntityAnimal)event.getTarget()).isInLove())
				{
					if (!event.getEntityPlayer().capabilities.isCreativeMode)
					{
						--itemstack.stackSize;

						if (itemstack.stackSize <= 0)
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
							event.getTarget().worldObj.spawnParticle(EnumParticleTypes.HEART, event.getTarget().posX + rand.nextFloat() * event.getTarget().width * 2.0F - event.getTarget().width, event.getTarget().posY + 0.5D + rand.nextFloat() * event.getTarget().height, event.getTarget().posZ + rand.nextFloat() * event.getTarget().width * 2.0F - event.getTarget().width, d0, d1, d2);	
						}
					}
				}
			}
		}
	}
}

