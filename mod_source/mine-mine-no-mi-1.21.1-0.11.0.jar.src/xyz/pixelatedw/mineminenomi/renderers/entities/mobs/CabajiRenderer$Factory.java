package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;

public class CabajiRenderer$Factory implements EntityRendererProvider<CabajiEntity> {
	public EntityRenderer<CabajiEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new CabajiRenderer(var1));
	}
}