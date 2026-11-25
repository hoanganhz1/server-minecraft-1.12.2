package xyz.pixelatedw.mineminenomi.renderers.blocks;

import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.WantedPosterData;
import xyz.pixelatedw.mineminenomi.blocks.WantedPosterBlock;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.WantedPosterBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.blocks.WantedPosterModel;

public class WantedPosterBlockEntityRenderer implements BlockEntityRenderer<WantedPosterBlockEntity> {
	// Fields
	private static final float[] SIZE_SCALE;
	private static final ResourceLocation TEXTURE;
	private final WantedPosterModel posterModel;
	private final WantedPosterModel face;
	private final WantedPosterModel faceOverlay;
	private final Font font;

	public WantedPosterBlockEntityRenderer(BlockEntityRendererProvider.Context var1) {
		this.posterModel = new WantedPosterModel(var1.bakeLayer(WantedPosterModel.LAYER_LOCATION));
		this.face = new WantedPosterModel(var1.bakeLayer(WantedPosterModel.FACE_LAYER_LOCATION));
		this.faceOverlay = new WantedPosterModel(var1.bakeLayer(WantedPosterModel.FACE_OVERLAY_LAYER_LOCATION));
		this.font = var1.getFont();
	}
	public void render(WantedPosterBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
		BlockState var7 = var1.getBlockState();
		WantedPosterData var8 = var1.getWantedPosterData();
		UUID var9 = var8.owner();
		String var10 = var8.ownerName();
		ResourceLocation var11 = null;
		int var12 = ((CanvasSize)(var7.getValue((Property)(WantedPosterBlock.SIZE)))).ordinal();
		float var13 = SIZE_SCALE[var12];
		var3.pushPose();
		var3.translate(0.5D,0D,0.5D);
		var3.mulPose(Axis.YP.rotationDegrees((-(((Direction)(var7.getValue((Property)(WantedPosterBlock.FACING)))).toYRot())) + (180F)));
		var3.mulPose(Axis.ZP.rotationDegrees(180F));
		var3.scale(var13,var13,1F);
		var3.pushPose();
		var3.mulPose(Axis.ZP.rotationDegrees(180F));
		var3.translate(0.005D,0.6D,0.5D);
		var3.scale(0.6F,-0.6F,-0.01F);
		VertexConsumer var14 = var4.getBuffer(ModRenderTypes.getWantedPoster(TEXTURE));
		this.posterModel.renderToBuffer(var3,var14,var5,var6);
		var3.popPose();
		if (Strings.isNullOrEmpty(var10)){
			var3.popPose();
			return;
		}else{
			String var15 = var8.getBountyString();
			var3.pushPose();
			if (var8.isExpired()){
				var3.pushPose();
				var3.translate(-0.345D,-0.9D,0.496D);
				var3.scale(0.173F,0.132F,0.001F);
				VertexConsumer var16 = var4.getBuffer(ModRenderTypes.getWantedPoster(ModResources.EXPIRED));
				RendererHelper.drawQuad(var3,var16,1F,1F,1F,1F,0F,0F,3.9F,3.9F,0F,1F,0F,1F,var5);
				var3.popPose();
			}
			var3.pushPose();
			if (var8.picture().isEmpty()){
				var11 = Minecraft.getInstance().getSkinManager().getInsecureSkin(new GameProfile(var8.owner(),var8.ownerName())).texture();
			}else{
				if (var8.picture().isPresent()){
					var11 = ((ResourceLocation)(var8.picture().get()));
				}else{
					var11 = DefaultPlayerSkin.get(var9).texture();
				}
			}
			var3.translate(-0.21D,-0.92D,0.498D);
			var3.scale(0.8F,0.8F,0.001F);
			VertexConsumer var16_1 = var4.getBuffer(ModRenderTypes.getWantedPoster(var11));
			this.face.renderToBuffer(var3,var16_1,var5,var6);
			this.faceOverlay.renderToBuffer(var3,var16_1,var5,var6);
			var3.popPose();
			var3.pushPose();
			if (var1.isPirate()){
				if (var8.jollyRoger().isPresent()){
					var3.translate(0.12F,-0.59F,0.497F);
					RenderSystem.enableDepthTest();
					((JollyRoger)(var8.jollyRoger().get())).render(var3,0,0,0.00065F);
					RenderSystem.disableDepthTest();
				}
			}else{
				if (var1.isRevolutionary()){
					var3.translate(-0.01F,-0.7F,0.497F);
					VertexConsumer var17 = var4.getBuffer(ModRenderTypes.getWantedPoster(ModResources.REVOLUTIONARY_ARMY_ICON));
					RendererHelper.drawQuad(var3,var17,1F,1F,1F,1F,0F,0F,0.4F,0.4F,0F,1F,0F,1F,var5);
				}
			}
			var3.popPose();
			var3.pushPose();
			var3.translate(-0.32D,-0.895D,0.499D);
			var3.scale(0.16F,0.126F,0.001F);
			String var17_1 = var8.background();
			String var18 = "textures/gui/wantedposters/backgrounds/" + var17_1 + ".png";
			RendererHelper.drawQuad(var3,var4.getBuffer(ModRenderTypes.getWantedPoster(ResourceLocation.fromNamespaceAndPath("mineminenomi",var18))),1F,1F,1F,1F,0F,0F,3.9F,3.9F,0F,1F,0F,1F,var5);
			var3.popPose();
			var3.popPose();
			var3.pushPose();
			var3.scale(0.007F,0.007F,0.007F);
			var3.translate(-10D,-42.5D,71D);
			if ((var10.length()) > (13)){
				String var19 = var10.substring(0,10);
				String var20 = var19 + "...";
				var10 = var20;
			}
			String var19_1 = String.valueOf((Object)(ChatFormatting.BOLD));
			String var20_1 = var10;
			String var21 = var19_1 + var20_1;
			this.font.drawInBatch(var21,(9F) - (((float)(this.font.width(var10))) / (1.75F)),-1F,-11455469,false,var3.last().pose(),var4,Font.DisplayMode.NORMAL,0,var5);
			var3.scale(1.2F,1.2F,1.2F);
			int var22 = ((var15.length()) > (9)) ? (1) : (0);
			if ((var22) != (0)){
				var3.pushPose();
				var3.translate(-5D,1.5D,0D);
				var3.scale(0.72F,0.89F,1.005F);
			}
			String var23 = String.valueOf((Object)(ChatFormatting.BOLD));
			String var24 = var15;
			String var25 = var23 + var24;
			this.font.drawInBatch(var25,-18F,9.5F,-11455469,false,var3.last().pose(),var4,Font.DisplayMode.NORMAL,0,var5);
			if ((var22) != (0)){
				var3.popPose();
			}
			var3.translate(0F,1F,0F);
			var3.scale(0.7F,0.9F,0.8F);
			String var26 = String.valueOf((Object)(ChatFormatting.BOLD));
			String var27 = var8.date();
			String var28 = var26 + var27;
			this.font.drawInBatch(var28,-40F,25F,-11455469,false,var3.last().pose(),var4,Font.DisplayMode.NORMAL,0,var5);
			var3.popPose();
			var3.popPose();
			return;
		}
	}
	static  {
		float[] var0 = new float[]{1F,2F,4F,6F};
		SIZE_SCALE = var0;
		TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/wantedposter.png");
	}
}