package panda.corn.other;

import java.util.List;

import javax.annotation.Nonnull;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import panda.corn.init.ModBlocks;
import panda.corn.init.ModItems;
import panda.corn.objects.BlockCorn;

@WailaPlugin
public class HwylaPlugin implements IWailaPlugin {
	
    @Override
    public void register(IWailaRegistrar registrar) {
		
        registrar.registerBodyProvider(new IWailaDataProvider() {
        	
            @Nonnull
            @Override
            public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

            	if(accessor.getMetadata() < 4){
            		currenttip.set(0,I18n.format("hud.msg.growth")+ " : "  + ((int) (((double) (accessor.getMetadata()) / 8) * 100)) + " %");
            	}else
            	if(accessor.getMetadata() == 4 && accessor.getWorld().getBlockState(accessor.getPosition().up()).getValue(BlockCorn.AGE) == 5 && accessor.getWorld().getBlockState(accessor.getPosition().up()).getBlock() == ModBlocks.CORN){
            		currenttip.set(0,I18n.format("hud.msg.growth")+ " : "  + ((int) (((double) (accessor.getMetadata()) / 8) * 100)) + " %");
            	}else
            	if(accessor.getMetadata() == 5){
            		currenttip.set(0,I18n.format("hud.msg.growth")+ " : 50 %");
            	}else
            	if(accessor.getMetadata() == 6 || (accessor.getMetadata() == 4 && accessor.getWorld().getBlockState(accessor.getPosition().up()).getValue(BlockCorn.AGE) == 6 &&accessor.getWorld().getBlockState(accessor.getPosition().up()).getBlock() == ModBlocks.CORN)){
                	currenttip.set(0,I18n.format("hud.msg.growth")+ " : 62 %");
                }else
                if((accessor.getMetadata() == 7 || accessor.getMetadata() == 8) || (accessor.getMetadata() == 4 && accessor.getWorld().getBlockState(accessor.getPosition().up()).getValue(BlockCorn.AGE) > 5 &&accessor.getWorld().getBlockState(accessor.getPosition().up()).getBlock() == ModBlocks.CORN)){
                    currenttip.set(0,I18n.format("hud.msg.growth")+ " : 75 %");
                }else
            	if(accessor.getMetadata() > 8){
            		currenttip.set(0,I18n.format("hud.msg.growth")+ " : " + I18n.format("hud.msg.mature"));
            	}
            	return currenttip;
            }
        }, BlockCorn.class);
        
        registrar.registerStackProvider(new IWailaDataProvider() {
        	
            @Nonnull
            @Override
			public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
            	return new ItemStack(ModItems.COB);
            }
        }, BlockCorn.class);
        
        registrar.registerHeadProvider(new IWailaDataProvider() {
        	
            @Nonnull
            @Override
            public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            	

            	return currenttip;
            }
        }, BlockCorn.class);
    }
}
