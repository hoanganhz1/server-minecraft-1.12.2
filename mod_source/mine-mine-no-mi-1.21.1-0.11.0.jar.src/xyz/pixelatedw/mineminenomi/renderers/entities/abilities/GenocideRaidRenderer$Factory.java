package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.GenocideRaidEffectEntity;

public class GenocideRaidRenderer$Factory implements EntityRendererProvider<GenocideRaidEffectEntity> {
	public EntityRenderer<GenocideRaidEffectEntity> create(EntityRendererProvider.Context var1) {
		return new GenocideRaidRenderer(var1);
	}
}