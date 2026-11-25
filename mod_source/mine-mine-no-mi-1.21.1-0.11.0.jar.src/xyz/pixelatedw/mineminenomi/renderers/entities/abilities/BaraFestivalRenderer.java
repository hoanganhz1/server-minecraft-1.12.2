package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.UUID;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraFestivalEntity;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.BaraFestivalModel;

@OnlyIn(value=Dist.CLIENT)
public class BaraFestivalRenderer<T extends BaraFestivalEntity> extends EntityRenderer<T> {
	// Fields
	private final BaraFestivalModel model;

	protected BaraFestivalRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.model = new BaraFestivalModel(var1.bakeLayer(BaraFestivalModel.LAYER_LOCATION));
		this.shadowRadius = 0F;
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		VertexConsumer var7 = var5.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(var1)));
		this.model.setupAnim(var1,0F,0F,(float)(var1.tickCount),0F,0F);
		this.model.renderToBuffer(var4,var7,var6,OverlayTexture.NO_OVERLAY);
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(T var1) {
		LivingEntity var2 = var1.getOwner();
		if (var2 instanceof AbstractClientPlayer){
			return ((AbstractClientPlayer)(var2)).getSkin().texture();
		}else{
			return DefaultPlayerSkin.get(((var2) != (null)) ? (var2.getUUID()) : (var1.getUUID())).texture();
		}
	}
}