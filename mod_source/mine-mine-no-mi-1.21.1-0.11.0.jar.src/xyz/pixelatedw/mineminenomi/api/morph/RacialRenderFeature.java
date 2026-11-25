package xyz.pixelatedw.mineminenomi.api.morph;

import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class RacialRenderFeature {
	// Fields
	private final HumanoidModel<? extends LivingEntity> model;
	private final ResourceLocation texture;
	private RacialRenderFeature$ILayerRenderExtension<? extends LivingEntity> extension;

	public RacialRenderFeature(HumanoidModel<? extends LivingEntity> var1, ResourceLocation var2) {
		this(var1,var2,null);
	}
	public RacialRenderFeature(HumanoidModel<? extends LivingEntity> var1, ResourceLocation var2, RacialRenderFeature$ILayerRenderExtension<? extends LivingEntity> var3) {
		this.model = var1;
		this.texture = var2;
		this.extension = var3;
	}
	public <T extends LivingEntity> HumanoidModel<T> getModel() {
		return (HumanoidModel<T>) (HumanoidModel) (this.model);
	}
	public ResourceLocation getTexture() {
		return this.texture;
	}
	@Nullable()
	public <T extends LivingEntity> RacialRenderFeature$ILayerRenderExtension<T> getExtension() {
		return (RacialRenderFeature$ILayerRenderExtension<T>) (RacialRenderFeature$ILayerRenderExtension) (this.extension);
	}
}