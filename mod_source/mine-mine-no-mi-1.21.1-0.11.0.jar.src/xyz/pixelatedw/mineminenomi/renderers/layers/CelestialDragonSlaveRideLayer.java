package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.CelestialDragonSlaveRideModel;

public class CelestialDragonSlaveRideLayer<E extends CelestialDragonEntity, M extends HumanoidModel<E>> extends RenderLayer<E, M> {
	// Fields
	private static final ResourceLocation VILLAGER_BASE_SKIN = ResourceLocation.parse("textures/entity/villager/villager.png");
	private CelestialDragonSlaveRideModel<LivingEntity> ride;

	public CelestialDragonSlaveRideLayer(EntityRendererProvider.Context var1, RenderLayerParent<E, M> var2) {
		super(var2);
		this.ride = (CelestialDragonSlaveRideModel) (new CelestialDragonSlaveRideModel(var1.bakeLayer(CelestialDragonSlaveRideModel.LAYER_LOCATION)));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, E var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if (!(var4.isRidingSlave())){
			return;
		}else{
			var1.pushPose();
			RenderType var11 = RenderType.entityCutout(VILLAGER_BASE_SKIN);
			this.ride.setupAnim(var4,var5,var6,var8,var9,var10);
			this.ride.renderToBuffer(var1,var2.getBuffer(var11),var3,OverlayTexture.NO_OVERLAY);
			var1.popPose();
			return;
		}
	}
}