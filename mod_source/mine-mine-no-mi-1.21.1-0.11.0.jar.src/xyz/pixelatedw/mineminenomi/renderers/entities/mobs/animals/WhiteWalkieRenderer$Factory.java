package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;

public class WhiteWalkieRenderer$Factory implements EntityRendererProvider<WhiteWalkieEntity> {
	public EntityRenderer<WhiteWalkieEntity> create(EntityRendererProvider.Context var1) {
		return new WhiteWalkieRenderer(var1);
	}
}