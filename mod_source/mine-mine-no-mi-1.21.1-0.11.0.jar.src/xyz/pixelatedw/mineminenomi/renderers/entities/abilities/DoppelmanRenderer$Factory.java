package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;

public class DoppelmanRenderer$Factory implements EntityRendererProvider<DoppelmanEntity> {
	public EntityRenderer<DoppelmanEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new DoppelmanRenderer(var1));
	}
}