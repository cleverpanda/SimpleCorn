package panda.corn.init;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import panda.corn.SimpleCorn;

@EventBusSubscriber(modid = SimpleCorn.MODID, bus = EventBusSubscriber.Bus.MOD)
public class GrassDrops {
	
	@SubscribeEvent
	public static void registerModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> ev) {
		ev.getRegistry().register(
				new GrassDropSerializer().setRegistryName(SimpleCorn.MODID, "kernel_drops")
		);
	}

	public static class GrassDropSerializer extends GlobalLootModifierSerializer<GrassDropModifier>{

		@Override
		public GrassDropModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition){
			return new GrassDropModifier(ailootcondition);
		}

		@Override
		public JsonObject write(GrassDropModifier instance){
			return new JsonObject();
		}
	}

	private static class GrassDropModifier extends LootModifier{
		protected GrassDropModifier(ILootCondition[] conditionsIn){
			super(conditionsIn);
		}

		@Nonnull
		@Override
		protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context){
			generatedLoot.add(new ItemStack(ModItems.KERNELS));
			return generatedLoot;
		}
	}
}