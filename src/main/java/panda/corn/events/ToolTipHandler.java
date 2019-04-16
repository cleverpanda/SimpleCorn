package panda.corn.events;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class ToolTipHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(value = Side.CLIENT) 
	public void onRenderTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if(stack.getItem() == Items.FIREWORK_CHARGE && stack.hasTagCompound() && stack.getTagCompound().getCompoundTag("Explosion").getBoolean("Popcorn")){
			event.getToolTip().remove(2);
			event.getToolTip().add("Popcorn");
		}
	}
}
