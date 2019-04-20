package panda.corn.other;

import java.util.List;
import javax.annotation.Nonnull;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import panda.corn.init.ModBlocks;
import panda.corn.init.ModItems;
import panda.corn.blocks.BlockCorn;

@WailaPlugin
public class HwylaPlugin implements IWailaPlugin {

    @Override
    public void register(IWailaRegistrar registrar) {
		
        registrar.registerBodyProvider(new IWailaDataProvider() {
        	
            @Nonnull
            @Override
            public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            	Block block = accessor.getBlock();
            	int age = accessor.getBlockState().getValue(BlockCorn.AGE);
            	currenttip.clear();
            	
            	if(block == ModBlocks.CORN){
            		if(age == 5){
            			currenttip.add(0, getPercent(7));
            		}else{
            			IBlockState above = accessor.getWorld().getBlockState(accessor.getPosition().up());
            			int upperAge = 0;
                		if(above instanceof BlockCorn){
                			upperAge = above.getValue(BlockCorn.AGE);
                		}
                		currenttip.add(0, getPercent(age + upperAge));
            		}
            		
            	}else
            	if(block == ModBlocks.CORN_MID){
            		currenttip.add(0, getPercent(age + 4));
                }else
                if(block == ModBlocks.CORN_TOP){
                	currenttip.add(0, getPercent(age + 6));
                }
            	
                return currenttip;

            }
        }, BlockCorn.class);
        
        registrar.registerStackProvider(new IWailaDataProvider() {   	
            @Nonnull
            @Override
			public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
            	return new ItemStack(ModItems.CORNCOB);
            }
        }, BlockCorn.class);
    }
    
    private String getPercent(int age){
		if(age == 7){
			return I18n.format("hud.msg.growth")+ " : "  + I18n.format("hud.msg.mature");
    	}else{
    		return I18n.format("hud.msg.growth")+ " : "  + (Math.round((((double) (age) / 7) * 100))) + " %";
    	}
    }
}