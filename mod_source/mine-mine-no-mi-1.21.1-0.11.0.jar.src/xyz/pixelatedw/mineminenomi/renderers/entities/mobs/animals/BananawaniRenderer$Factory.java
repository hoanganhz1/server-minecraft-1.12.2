package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;

public class BananawaniRenderer$Factory implements EntityRendererProvider<BananawaniEntity> {
	public EntityRenderer<BananawaniEntity> create(EntityRendererProvider.Context var1) {
		return new BananawaniRenderer(var1);
	}
}