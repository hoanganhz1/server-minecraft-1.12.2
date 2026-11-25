package xyz.pixelatedw.mineminenomi.renderers.layers.hairs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.awt.Color;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;

public class CelestialDragonWigLayer<T extends CelestialDragonEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/armor/celestial_dragon_wig.png");
	private final HumanoidModel<T> wigModel;

	public CelestialDragonWigLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.wigModel = new HumanoidModel(var1.bakeLayer(ModelLayers.ZOMBIE));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		RenderType var11 = RenderType.entityCutoutNoCull(TEXTURE);
		((HumanoidModel)(this.getParentModel())).copyPropertiesTo(this.wigModel);
		this.wigModel.setupAnim(var4,var5,var6,var8,var9,var10);
		float var12 = ((float)(var4.getHairColor().getRed())) / (255F);
		float var13 = ((float)(var4.getHairColor().getGreen())) / (255F);
		float var14 = ((float)(var4.getHairColor().getBlue())) / (255F);
		this.wigModel.renderToBuffer(var1,var2.getBuffer(var11),var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(var12,var13,var14,1F));
	}
}