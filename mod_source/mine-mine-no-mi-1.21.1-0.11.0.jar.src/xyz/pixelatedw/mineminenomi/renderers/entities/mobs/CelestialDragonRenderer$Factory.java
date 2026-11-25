package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;

public class CelestialDragonRenderer$Factory extends OPHumanoidRenderer$Factory<CelestialDragonEntity> {
	public CelestialDragonRenderer$Factory(EntityRendererProvider.Context var1) {
		super(var1);
	}
	public EntityRenderer<CelestialDragonEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new CelestialDragonRenderer(var1));
	}
}