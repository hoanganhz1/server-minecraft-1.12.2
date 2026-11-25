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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.LapahnModel;

public class LapahnRenderer<T extends LapahnEntity, M extends LapahnModel<T>> extends MobRenderer<T, M> {
	// Fields
	private static final ResourceLocation NORMAL_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/lapahn.png");
	private static final ResourceLocation ENRAGED_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/lapahn_angry.png");

	public LapahnRenderer(EntityRendererProvider.Context var1) {
		super(var1,(EntityModel)(new LapahnModel(var1.bakeLayer(LapahnModel.LAYER_LOCATION))),0.8F);
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		super.render(var1,var2,var3,var4,var5,var6);
	}
	protected void setupRotations(T var1, PoseStack var2, float var3, float var4, float var5, float var6) {
		super.setupRotations(var1,var2,var3,var4,var5,var6);
		if (var1.isResting()){
			var2.translate(0D,-0.45D,0D);
		}
	}
	public ResourceLocation getTextureLocation(T var1) {
		return (var1.isEnraged()) ? (ENRAGED_TEXTURE) : (NORMAL_TEXTURE);
	}
}