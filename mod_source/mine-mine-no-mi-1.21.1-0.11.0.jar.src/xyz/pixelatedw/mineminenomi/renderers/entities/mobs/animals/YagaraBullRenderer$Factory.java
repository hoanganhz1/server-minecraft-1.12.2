package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.YagaraBullEntity;

public class YagaraBullRenderer$Factory implements EntityRendererProvider<YagaraBullEntity> {
	public EntityRenderer<YagaraBullEntity> create(EntityRendererProvider.Context var1) {
		return new YagaraBullRenderer(var1);
	}
}