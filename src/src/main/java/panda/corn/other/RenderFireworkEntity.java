package panda.corn.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFireworkEntity implements IRenderFactory<Entity> {

	public static final RenderFireworkEntity INSTANCE = new RenderFireworkEntity();
	
	@Override
	public Render<? super Entity> createRenderFor(RenderManager manager) {
		return new RenderSnowball(manager, Items.FIREWORKS, Minecraft.getMinecraft().getRenderItem());
	}

}
