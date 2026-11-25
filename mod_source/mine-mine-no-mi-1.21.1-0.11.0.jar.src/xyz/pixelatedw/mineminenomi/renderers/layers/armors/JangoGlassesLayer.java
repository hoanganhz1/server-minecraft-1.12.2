package xyz.pixelatedw.mineminenomi.renderers.layers.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.models.armors.HeartGlassesModel;

public class JangoGlassesLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/armor/heart_glasses.png");
	private final HeartGlassesModel<T> model;

	public JangoGlassesLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new HeartGlassesModel(var1.bakeLayer(HeartGlassesModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		var1.pushPose();
		var1.translate(0D,0.05D,0D);
		RenderType var11 = RenderType.entityTranslucent(TEXTURE);
		((HumanoidModel)(this.getParentModel())).copyPropertiesTo((HumanoidModel)(this.model));
		this.model.setupAnim(var4,var5,var6,var8,var9,var10);
		this.model.renderToBuffer(var1,var2.getBuffer(var11),var3,OverlayTexture.NO_OVERLAY);
		var1.popPose();
	}
}