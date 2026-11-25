package xyz.pixelatedw.mineminenomi.renderers.entities.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.StrikerModel;

public class StrikerRenderer extends EntityRenderer<StrikerEntity> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/striker.png");
	private final StrikerModel model;

	protected StrikerRenderer(EntityRendererProvider.Context var1, StrikerModel var2) {
		super(var1);
		this.model = var2;
	}
	public void render(StrikerEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var4.translate(0D,2D,0D);
		var4.scale(1.4F,1.4F,1.4F);
		var4.mulPose(Axis.YP.rotationDegrees((180F) - (var2)));
		float var7 = ((float)(var1.getHurtTime())) - (var3);
		if ((var7) > (0F)){
			var4.mulPose(Axis.ZP.rotationDegrees((Mth.sin(var7)) * (var7)));
			var4.mulPose(Axis.XN.rotationDegrees((Mth.sin(var7)) * (var7)));
		}
		var4.scale(-1F,-1F,1F);
		this.model.setupAnim(var1,var3,0F,-0.1F,0F,0F);
		VertexConsumer var8 = var5.getBuffer(this.model.renderType(this.getTextureLocation(var1)));
		this.model.renderToBuffer(var4,var8,var6,OverlayTexture.NO_OVERLAY);
		var4.pushPose();
		var4.translate(-0.7D,-1D,0.61D);
		var4.scale(1.5F,1.5F,1.5F);
		Player var9 = var1.getLastRider();
		if ((var9) != (null)){
			StrikerEntity var10 = var1;
			Player var11 = var9;
			PoseStack var12 = var4;
			MultiBufferSource var13 = var5;
			int var14 = var6;
			final StrikerEntity var10_f1 = var10;
			final Player var11_f2 = var11;
			final PoseStack var12_f3 = var12;
			final MultiBufferSource var13_f4 = var13;
			final int var14_f5 = var14;
			Consumer<EntityStatsData> var15 = (l0) -> {
			if ((l0.getFaction().isPresent()) && ((var10_f1.getCrew()) != (null))){
				RendererHelper.drawPlayerFactionIcon(((Faction)(l0.getFaction().get())),var10_f1.getCrew().getJollyRoger(),(LivingEntity)(var11_f2),var12_f3,var13_f4,var14_f5);
			}
		};
			((java.util.Optional)(EntityStatsData.get((LivingEntity)(var9)))).ifPresent((Consumer)(var15));
		}
		var4.popPose();
		var4.popPose();
		super.render((Entity)(var1),var2,var3,var4,var5,var6);
	}
	public ResourceLocation getTextureLocation(StrikerEntity var1) {
		return TEXTURE;
	}
}