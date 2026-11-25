package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.HanaHandsEntity;

public class HanaHandsRenderer$Factory implements EntityRendererProvider<HanaHandsEntity> {
	public EntityRenderer<HanaHandsEntity> create(EntityRendererProvider.Context var1) {
		return new HanaHandsRenderer(var1);
	}
}