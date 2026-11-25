package xyz.pixelatedw.mineminenomi.renderers.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.blocks.FlagBlock;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.blocks.FlagModel;

public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {
	// Fields
	public static final ResourceLocation ON_FIRE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/misc/flagflame.png");
	private static final float[] SIZE_SCALE;
	private static final float[] OFFSET_X;
	private static final float[] OFFSET_Y;
	private FlagModel flagModel;

	public FlagBlockEntityRenderer(BlockEntityRendererProvider.Context var1) {
		this.flagModel = new FlagModel(var1.bakeLayer(FlagModel.LAYER_LOCATION));
	}
	public void render(FlagBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
		if (var1.isSub()){
			return;
		}else{
			Level var7 = var1.getLevel();
			Player var8 = var7.getPlayerByUUID(var1.getOwnerUUID());
			BlockState var9 = var1.getBlockState();
			int var10 = ((CanvasSize)(var9.getValue((Property)(FlagBlock.SIZE)))).ordinal();
			float var11 = SIZE_SCALE[var10];
			Direction var12 = ((Direction)(var9.getValue((Property)(FlagBlock.FACING)))).getOpposite();
			BlockPos var13 = var1.getBlockPos().offset(var12.getStepX(),var12.getStepY(),var12.getStepZ());
			double var14 = (1D) - (var7.getBlockState(var13).getShape((BlockGetter)(var7),var13).max(var12.getAxis()));
			var3.translate(0.5F,0F,0.5F);
			var3.mulPose(Axis.YP.rotationDegrees((-(((Direction)(var9.getValue((Property)(FlagBlock.FACING)))).toYRot())) + (90F)));
			var3.translate(var14,0D,0D);
			var3.scale(var11,var11,1F);
			var3.translate(OFFSET_X[var10],OFFSET_Y[var10],0.5F);
			long var15 = (var7.getGameTime()) + ((long)((((java.lang.Object)(var1)).hashCode()) / (100000)));
			var3.pushPose();
			float var16 = (float)((Math.cos(((double)(((float)(var15)) * (0.1F))) + (3.141592653589793D))) * (0.019999999552965164D));
			var3.pushPose();
			var3.translate(0.65D,(0.9D) - ((double)(var16)),-0.537D);
			var3.scale(-1F,-1F,1F);
			var3.scale(0.9F,0.9F,1F);
			var3.mulPose(Axis.YP.rotation((Mth.cos((((float)(var15)) * (0.12F)) + (3.1415927F))) * (0.023F)));
			if (var1.getFaction().isPresent()){
				RendererHelper.drawPlayerFactionIcon(((Faction)(var1.getFaction().get())),((JollyRoger)(((java.util.Optional)(var1.getJollyRoger())).orElse((Object)(null)))),null,var3,var4,var5);
			}
			var3.popPose();
			var3.pushPose();
			var3.translate(0.75D,(0.9D) - ((double)(var16)),-0.485D);
			var3.scale(1F,-1F,1F);
			var3.scale(0.9F,0.9F,1F);
			var3.mulPose(Axis.YN.rotation((Mth.cos((((float)(var15)) * (0.12F)) + (3.1415927F))) * (0.02F)));
			var3.translate(-1D,0D,0D);
			if (var1.getFaction().isPresent()){
				RendererHelper.drawPlayerFactionIcon(((Faction)(var1.getFaction().get())),((JollyRoger)(((java.util.Optional)(var1.getJollyRoger())).orElse((Object)(null)))),null,var3,var4,var5);
			}
			var3.popPose();
			var3.popPose();
			RenderType var17 = ModRenderTypes.FLAG;
			Color var18 = ((Color)(var1.getFaction().map((Function)(((Function<Faction, Color>)(Faction::getFlagBackgroundColor)))).orElse(Color.WHITE)));
			if (var1.isOnFire()){
				var17 = ModRenderTypes.FLAG_ON_FIRE;
				var3.pushPose();
				var3.translate(0.25D,0D,-0.6D);
				this.renderFlame(var3,var4,1F,0.55F);
				var3.scale(-1F,1F,1F);
				var3.translate(0D,0D,0.15D);
				this.renderFlame(var3,var4,1F,0.55F);
				var3.popPose();
			}
			var3.pushPose();
			var3.translate(0.5D,1.5D,-0.51D);
			var3.scale(-1F,-1F,1F);
			VertexConsumer var19 = var4.getBuffer(var17);
			this.flagModel.flagAnimation(var7,var15);
			this.flagModel.renderToBuffer(var3,var19,var5,var6,var18.getRGB());
			var3.popPose();
			return;
		}
	}
	public void renderFlame(PoseStack var1, MultiBufferSource var2, float var3, float var4) {
		TextureAtlasSprite var5 = ModelBakery.FIRE_0.sprite();
		TextureAtlasSprite var6 = ModelBakery.FIRE_1.sprite();
		var1.pushPose();
		float var7 = (var3) * (1.4F);
		var1.scale(var7,var7,var7);
		float var8 = 0.5F;
		float var9 = (var4) / (var7);
		float var10 = 0F;
		float var11 = 0F;
		int var12 = 0;
		VertexConsumer var13 = var2.getBuffer(Sheets.cutoutBlockSheet());
		PoseStack.Pose var14 = var1.last();
		do{
			if ((var9) > (0F)){
				TextureAtlasSprite var15 = (((var12) % (2)) == (0)) ? (var5) : (var6);
				float var16 = var15.getU0();
				float var17 = var15.getV0();
				float var18 = var15.getU1();
				float var19 = var15.getV1();
				if ((((var12) / (2)) % (2)) == (0)){
					float var20 = var18;
					var18 = var16;
					var16 = var20;
				}
				fireVertex(var14,var13,(var8) - (0F),(0F) - (var10),var11,var18,var19);
				fireVertex(var14,var13,(-(var8)) - (0F),(0F) - (var10),var11,var16,var19);
				fireVertex(var14,var13,(-(var8)) - (0F),(1.4F) - (var10),var11,var16,var17);
				fireVertex(var14,var13,(var8) - (0F),(1.4F) - (var10),var11,var18,var17);
				var9 = (var9) - (0.45F);
				var10 = (var10) - (0.45F);
				var8 = (var8) * (0.9F);
				var11 = (var11) + (0.03F);
				var12++;
				continue;
			}else{
				break;
			}
		} while (true);
		var1.popPose();
	}
	private static void fireVertex(PoseStack.Pose var0, VertexConsumer var1, float var2, float var3, float var4, float var5, float var6) {
		var1.addVertex(var0.pose(),var2,var3,var4).setColor(255,255,255,255).setUv(var5,var6).setUv2(240,0).setNormal(var0,0F,1F,0F);
	}
	public AABB getRenderBoundingBox(FlagBlockEntity var1) {
		return AABB.ofSize(Vec3.atCenterOf((Vec3i)(var1.getBlockPos())),10D,10D,10D);
	}
	public int getViewDistance() {
		return 256;
	}
	static  {
		float[] var0 = new float[]{1F,2F,4F,6F};
		SIZE_SCALE = var0;
		float[] var1 = new float[]{-0.5F,-0.75F,-0.87F,-0.916F};
		OFFSET_X = var1;
		float[] var2 = new float[]{0F,-0.5F,-0.75F,-0.833F};
		OFFSET_Y = var2;
	}
}