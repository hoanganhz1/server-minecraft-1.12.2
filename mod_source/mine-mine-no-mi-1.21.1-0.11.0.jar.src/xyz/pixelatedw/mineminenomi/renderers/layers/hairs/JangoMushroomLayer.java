package xyz.pixelatedw.mineminenomi.renderers.layers.hairs;

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
import xyz.pixelatedw.mineminenomi.models.armors.JangoMushroomModel;

public class JangoMushroomLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/armor/jango_mushroom.png");
	private final JangoMushroomModel<T> model;

	public JangoMushroomLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new JangoMushroomModel(var1.bakeLayer(JangoMushroomModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		RenderType var11 = RenderType.entityCutout(TEXTURE);
		((HumanoidModel)(this.getParentModel())).copyPropertiesTo((HumanoidModel)(this.model));
		this.model.setupAnim(var4,var5,var6,var8,var9,var10);
		this.model.renderToBuffer(var1,var2.getBuffer(var11),var3,OverlayTexture.NO_OVERLAY);
	}
}