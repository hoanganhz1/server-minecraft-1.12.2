package xyz.pixelatedw.mineminenomi.renderers.morphs;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GearFourthPointRenderer$Factory<M extends EntityModel<LivingEntity> & ArmedModel & HeadedModel> implements EntityRendererProvider<LivingEntity> {
	// Fields
	private MorphInfo info;
	private M model;
	private boolean hasCulling;

	public GearFourthPointRenderer$Factory(MorphInfo var1, M var2) {
		this.info = var1;
		this.model = var2;
	}
	public GearFourthPointRenderer$Factory(MorphInfo var1, boolean var2) {
		this.info = var1;
		this.hasCulling = var2;
	}
	public EntityRenderer<LivingEntity> create(EntityRendererProvider.Context var1) {
		GearFourthPointRenderer var2 = new GearFourthPointRenderer(var1,this.info,this.model);
		var2.setCullingState(this.hasCulling);
		return var2;
	}
}