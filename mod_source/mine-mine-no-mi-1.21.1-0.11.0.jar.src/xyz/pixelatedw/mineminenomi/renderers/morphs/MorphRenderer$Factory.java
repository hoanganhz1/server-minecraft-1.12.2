package xyz.pixelatedw.mineminenomi.renderers.morphs;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class MorphRenderer$Factory<M extends EntityModel<LivingEntity> & ArmedModel & HeadedModel> implements EntityRendererProvider<LivingEntity> {
	// Fields
	 MorphInfo info;
	 M model;
	private boolean hasCulling;

	public MorphRenderer$Factory(MorphInfo var1, M var2) {
		this.info = var1;
		this.model = var2;
	}
	public MorphRenderer$Factory(MorphInfo var1, boolean var2) {
		this.info = var1;
		this.hasCulling = var2;
	}
	public EntityRenderer<LivingEntity> create(EntityRendererProvider.Context var1) {
		MorphRenderer var2 = new MorphRenderer(var1,this.info,this.model);
		var2.setCullingState(this.hasCulling);
		return var2;
	}
}