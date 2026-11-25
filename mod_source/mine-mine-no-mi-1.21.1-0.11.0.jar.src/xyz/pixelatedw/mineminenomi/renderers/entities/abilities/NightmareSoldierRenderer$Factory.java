package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;

public class NightmareSoldierRenderer$Factory implements EntityRendererProvider<NightmareSoldierEntity> {
	public EntityRenderer<NightmareSoldierEntity> create(EntityRendererProvider.Context var1) {
		return (EntityRenderer) (new NightmareSoldierRenderer(var1));
	}
}