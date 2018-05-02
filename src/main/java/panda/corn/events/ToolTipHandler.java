package panda.corn.events;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class ToolTipHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(value = Side.CLIENT) 
	public void onRenderTooltip(ItemTooltipEvent event) {
		if(event.getItemStack().getItem() == Items.FIREWORK_CHARGE && event.getItemStack().hasTagCompound()){
			if(event.getItemStack().getTagCompound().getCompoundTag("Explosion").getBoolean("Popcorn")){
				event.getToolTip().remove(2);
				event.getToolTip().add("Popcorn");
			}
		}
	}
}
