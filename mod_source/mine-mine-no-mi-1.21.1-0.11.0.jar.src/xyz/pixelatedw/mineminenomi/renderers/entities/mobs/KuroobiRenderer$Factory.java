package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.KuroobiEntity;

public class KuroobiRenderer$Factory implements EntityRendererProvider<KuroobiEntity> {
	public EntityRenderer<KuroobiEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new KuroobiRenderer(var1));
	}
}