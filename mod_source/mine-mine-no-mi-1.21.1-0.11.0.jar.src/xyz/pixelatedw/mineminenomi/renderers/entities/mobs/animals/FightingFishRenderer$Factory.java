package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FightingFishEntity;

public class FightingFishRenderer$Factory implements EntityRendererProvider<FightingFishEntity> {
	public EntityRenderer<FightingFishEntity> create(EntityRendererProvider.Context var1) {
		return new FightingFishRenderer(var1);
	}
}