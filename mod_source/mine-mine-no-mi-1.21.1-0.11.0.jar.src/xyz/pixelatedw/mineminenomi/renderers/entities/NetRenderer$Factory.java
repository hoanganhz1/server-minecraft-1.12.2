package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;

public class NetRenderer$Factory implements EntityRendererProvider<NetEntity> {
	public EntityRenderer<NetEntity> create(EntityRendererProvider.Context var1) {
		return new NetRenderer(var1);
	}
}