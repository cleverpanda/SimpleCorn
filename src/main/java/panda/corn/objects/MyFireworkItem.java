package panda.corn.objects;

import java.util.List;

import javax.annotation.Nullable;

import panda.corn.entity.MyEntityFireworkRocket;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import com.google.common.collect.Lists;

public class MyFireworkItem extends ItemFirework{
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
        	ItemStack stack = player.getHeldItem(hand);
            MyEntityFireworkRocket entityfireworkrocket = new MyEntityFireworkRocket(worldIn, pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ, stack);
            worldIn.spawnEntity(entityfireworkrocket);

            if (!player.capabilities.isCreativeMode)
            {
                stack.shrink(1);
            }
        }

        return EnumActionResult.SUCCESS;
    }

	@Override
	public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = stack.getSubCompound("Fireworks");

            if (nbttagcompound != null)
            {
                if (nbttagcompound.hasKey("Flight", 99))
                {
                    tooltip.add(I18n.format("item.fireworks.flight") + " " + nbttagcompound.getByte("Flight"));
                }

                NBTTagList nbttaglist = nbttagcompound.getTagList("Explosions", 10);

                if (nbttaglist != null && !nbttaglist.hasNoTags())
                {
                        NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(1);
                        List<String> list = Lists.<String>newArrayList();
                        ItemFireworkCharge.addExplosionInfo(nbttagcompound1, list);

                        if (!list.isEmpty())
                        {
                            for (int j = 1; j < list.size(); ++j)
                            {
                                list.set(j, "  " + list.get(j));
                            }

                            tooltip.addAll(list);
                        }
                    tooltip.add(I18n.format("item.simplecorn.poppedcorn.name"));  
                }
            }
        }
    }
	

}