package panda.corn;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.ai.EntityAITempt;

public class ChickenFollowHandler {

	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		 if(event.entity instanceof EntityChicken && !event.world.isRemote){
			 EntityChicken chicken = (EntityChicken)event.entity;
			 chicken.tasks.addTask(8, new EntityAITempt(chicken, 1.0D, Corn.kernels, false));
		 }
	}
}
