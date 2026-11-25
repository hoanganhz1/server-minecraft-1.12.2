package xyz.pixelatedw.mineminenomi.renderers.morphs;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class PlayerMorphRenderer$Factory implements EntityRendererProvider<LivingEntity> {
	// Fields
	private MorphInfo info;
	private HumanoidModel<LivingEntity> model;
	private boolean isSlim;

	public PlayerMorphRenderer$Factory(MorphInfo var1, HumanoidModel<LivingEntity> var2, boolean var3) {
		this.info = var1;
		this.model = var2;
		this.isSlim = var3;
	}
	public EntityRenderer<LivingEntity> create(EntityRendererProvider.Context var1) {
		return new PlayerMorphRenderer(var1,this.info,this.model,this.isSlim);
	}
}