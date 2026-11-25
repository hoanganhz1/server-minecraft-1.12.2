package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.projectiles.ThrowingWeaponEntity;

public class ThrowingWeaponRenderer$Factory implements EntityRendererProvider<ThrowingWeaponEntity> {
	public EntityRenderer<ThrowingWeaponEntity> create(EntityRendererProvider.Context var1) {
		return new ThrowingWeaponRenderer(var1);
	}
}