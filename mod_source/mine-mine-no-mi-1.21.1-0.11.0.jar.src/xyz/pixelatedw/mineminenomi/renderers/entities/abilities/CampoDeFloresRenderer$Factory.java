package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.CampoDeFloresEntity;

public class CampoDeFloresRenderer$Factory implements EntityRendererProvider<CampoDeFloresEntity> {
	public EntityRenderer<CampoDeFloresEntity> create(EntityRendererProvider.Context var1) {
		return new CampoDeFloresRenderer(var1);
	}
}