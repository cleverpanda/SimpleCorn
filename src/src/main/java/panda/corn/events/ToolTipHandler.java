package panda.corn.events;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
public class ToolTipHandler {
	
	@SubscribeEvent
	public void onRenderTooltip(ItemTooltipEvent event) {
		if(event.getItemStack().getItem() == Items.FIREWORK_CHARGE){
			if(event.getItemStack().hasTagCompound()){
				if(event.getItemStack().getTagCompound().getCompoundTag("Explosion").getBoolean("Popcorn")){
					event.getToolTip().remove(2);
					event.getToolTip().add("Popcorn");
				}
			}
		}
	}
}
