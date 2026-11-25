package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
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
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.entities.PhysicalBodyEntity;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class PhysicalBodyRenderer<T extends PhysicalBodyEntity> extends EntityRenderer<T> {
	// Fields
	private static final ResourceLocation SPOOKY_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/yomi_skeleton.png");
	private HumanoidModel<T> model;
	private HumanoidModel<?> slimModel;
	private HumanoidModel<?> wideModel;

	public PhysicalBodyRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0.7F;
		this.slimModel = (HumanoidModel) (new HumanoidModel(var1.bakeLayer(ModelLayers.PLAYER_SLIM)));
		this.wideModel = (HumanoidModel) (new HumanoidModel(var1.bakeLayer(ModelLayers.PLAYER)));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		int var7 = 0;
		Player var8 = var1.getOwner();
		if (var8 instanceof AbstractClientPlayer){
			var7 = ((((AbstractClientPlayer)(var8)).getSkin().model()) == (PlayerSkin.Model.SLIM)) ? (1) : (0);
		}
		this.model = ((var7) != (0)) ? (this.slimModel) : (this.wideModel);
		var4.pushPose();
		var4.translate(0D,0.8D,0D);
		var4.mulPose(Axis.ZP.rotationDegrees(180F));
		var4.mulPose(Axis.YP.rotationDegrees((var1.getXRot()) + (180F)));
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		this.model.young = false;
		this.model.riding = true;
		this.model.setupAnim(var1,0F,0F,0F,0F,0F);
		ResourceLocation var9 = this.getTextureLocation(var1);
		VertexConsumer var10 = var5.getBuffer(this.model.renderType(var9));
		this.model.renderToBuffer(var4,var10,var6,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,1F,1F,1F));
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(PhysicalBodyEntity var1) {
		Player var2 = var1.getOwner();
		if ((var2) != (null)){
			DevilFruitData var4 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1.getOwner())))).orElse((Object)(null))));
			if (((var4) != (null)) && ((var4.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI)) && (((MorphInfo)(ModMorphs.YOMI_SKELETON.get())).isActive((LivingEntity)(var1.getOwner()))))){
				return SPOOKY_TEXTURE;
			}else{
				return ((AbstractClientPlayer)(var2)).getSkin().texture();
			}
		}else{
			UUID var3 = var1.getOwnerUUID();
			if ((var3) != (null)){
				return DefaultPlayerSkin.get(var3).texture();
			}else{
				return DefaultPlayerSkin.getDefaultTexture();
			}
		}
	}
	public void setSpooky(EntityRendererProvider.Context var1) {
		this.slimModel = new SkeletonModel(var1.bakeLayer(ModelLayers.SKELETON));
		this.wideModel = new SkeletonModel(var1.bakeLayer(ModelLayers.SKELETON));
	}
}