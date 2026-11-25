package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.ChargingUrsusShockEntity;

public class ChargingUrsusShockRenderer$Factory implements EntityRendererProvider<ChargingUrsusShockEntity> {
	public EntityRenderer<ChargingUrsusShockEntity> create(EntityRendererProvider.Context var1) {
		return new ChargingUrsusShockRenderer(var1);
	}
}