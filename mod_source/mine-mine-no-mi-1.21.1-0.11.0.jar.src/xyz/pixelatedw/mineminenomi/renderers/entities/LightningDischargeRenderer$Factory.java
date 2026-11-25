package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;

public class LightningDischargeRenderer$Factory implements EntityRendererProvider<LightningDischargeEntity> {
	public EntityRenderer<LightningDischargeEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new LightningDischargeRenderer(var1));
	}
}