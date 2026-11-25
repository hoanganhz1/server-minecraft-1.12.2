package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FlyingFishEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.FlyingFishModel;

public class FlyingFishRenderer<T extends FlyingFishEntity, M extends FlyingFishModel<T>> extends MobRenderer<T, M> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/flying_fish.png");

	public FlyingFishRenderer(EntityRendererProvider.Context var1) {
		super(var1,(EntityModel)(new FlyingFishModel(var1.bakeLayer(FlyingFishModel.LAYER_LOCATION))),1.5F);
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if (var1.isSaddled()){
			((FlyingFishModel)(this.model)).renderSaddle();
		}
		super.render(var1,var2,var3,var4,var5,var6);
	}
	public ResourceLocation getTextureLocation(T var1) {
		return TEXTURE;
	}
}