package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.HumandrillEntity;

public class HumandrillRenderer$Factory implements EntityRendererProvider<HumandrillEntity> {
	public EntityRenderer<HumandrillEntity> create(EntityRendererProvider.Context var1) {
		return new HumandrillRenderer(var1);
	}
}