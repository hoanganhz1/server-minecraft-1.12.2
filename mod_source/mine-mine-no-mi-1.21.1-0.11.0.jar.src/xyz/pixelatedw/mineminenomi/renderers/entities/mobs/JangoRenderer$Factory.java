package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.JangoEntity;

public class JangoRenderer$Factory implements EntityRendererProvider<JangoEntity> {
	public EntityRenderer<JangoEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new JangoRenderer(var1));
	}
}