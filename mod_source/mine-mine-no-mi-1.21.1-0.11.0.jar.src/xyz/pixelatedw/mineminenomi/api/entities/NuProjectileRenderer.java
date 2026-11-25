package xyz.pixelatedw.mineminenomi.api.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class NuProjectileRenderer<T extends NuProjectileEntity, M extends EntityModel<T>> extends EntityRenderer<T> {
	// Fields
	private static final int DEFAULT_COLOR;
	 float scaleX;
	 float scaleY;
	 float scaleZ;
	 float rotationX;
	 float rotationY;
	 float rotationZ;
	 Color colour;
	protected Supplier<Color> calcColor;
	protected @Nullable() M model;
	 ResourceLocation texture;
	 boolean useArmSkin;
	 boolean useLegSkin;
	 boolean isGlowing;
	private EntityRenderer<? super Entity> ownerRenderer;

	public NuProjectileRenderer(EntityRendererProvider.Context var1, @Nullable() M var2) {
		super(var1);
		this.scaleX = 1F;
		this.scaleY = 1F;
		this.scaleZ = 1F;
		this.rotationX = 0F;
		this.rotationY = 0F;
		this.rotationZ = 0F;
		this.calcColor = null;
		this.useArmSkin = false;
		this.useLegSkin = false;
		this.model = var2;
	}
	public void setUseArmSkin() {
		this.useArmSkin = true;
	}
	public void setUseLegSkin() {
		this.useLegSkin = true;
	}
	public void setGlowing() {
		this.isGlowing = true;
	}
	public void setTexture(ResourceLocation var1) {
		this.texture = var1;
	}
	public Color getColor() {
		return this.colour;
	}
	public void setColor(Color var1) {
		this.colour = var1;
	}
	public void setScale(float var1, float var2, float var3) {
		this.scaleX = var1;
		this.scaleY = var2;
		this.scaleZ = var3;
	}
	public void setScale(Vec3 var1) {
	}
	public void setScale(float var1) {
		this.setScale(var1,var1,var1);
	}
	public Vec3 getScale() {
		return new Vec3((double)(this.scaleX),(double)(this.scaleY),(double)(this.scaleZ));
	}
	public void setRotation(float var1, float var2, float var3) {
		this.rotationX = var1;
		this.rotationY = var2;
		this.rotationZ = var3;
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if ((var1.tickCount) < (2)){
			return;
		}else{
			if (var1 instanceof IFlexibleSizeProjectile){
				this.setScale(((IFlexibleSizeProjectile)(var1)).getSize());
			}
			if ((Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) && ((!(var1.isInvisible())) && (!(Minecraft.getInstance().showOnlyReducedInfo())))){
				this.renderDebugBox(var4,var5.getBuffer(RenderType.lines()),var1);
			}
			var4.pushPose();
			var4.mulPose(Axis.YP.rotationDegrees(((Mth.lerp(var3,var1.yRotO,var1.getYRot())) + (180F)) + (this.rotationX)));
			var4.mulPose(Axis.XP.rotationDegrees((Mth.lerp(var3,var1.xRotO,var1.getXRot())) + (this.rotationY)));
			var4.mulPose(Axis.ZP.rotationDegrees(this.rotationZ));
			var4.scale(-1F,-1F,1F);
			var4.scale(this.scaleX,this.scaleY,this.scaleZ);
			float var7 = ((float)(var1.tickCount)) + (var3);
			Color var8 = this.getColor();
			if ((this.calcColor) != (null)){
				var8 = ((Color)(this.calcColor.get()));
			}
			if ((this.model) != (null)){
				this.model.setupAnim(var1,0F,0F,var7,0F,0F);
				VertexConsumer var9 = var5.getBuffer(this.getRenderType(var1));
				this.model.renderToBuffer(var4,var9,var6,OverlayTexture.NO_OVERLAY,var8.getRGB());
				this.applyModelOverlay(var1,(M)(this.model),var4,var5,var6);
			}else{
				if (((!(this.useArmSkin)) && (!(this.useLegSkin))) || ((var1.getOwner()) == (null))){

				}else{
					if ((this.ownerRenderer) == (null)){
						this.ownerRenderer = (EntityRenderer) (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer((Entity)(var1.getOwner())));
					}
					if (this.useArmSkin){
						var4.translate(0.3D,0D,0.5D);
					}else{
						if (this.useLegSkin){
							var4.translate(0.1D,0D,1D);
						}
					}
					var4.mulPose(Axis.XN.rotationDegrees(90F));
					if ((this.ownerRenderer) != (null)){
						EntityRenderer var9_1 = this.ownerRenderer;
						if (var9_1 instanceof LivingEntityRenderer){
							LivingEntityRenderer var10 = ((LivingEntityRenderer)(var9_1));
							List var11 = null;
							if (this.useArmSkin){
								var11 = RendererHelper.getArmPartsFrom(var10.getModel());
							}
							if (this.useLegSkin){
								var11 = RendererHelper.getLegPartsFrom(var10.getModel());
							}
							if (((var11) != (null)) && (!(var11.isEmpty()))){
								Iterator var12 = var11.iterator();
								do{
									if (var12.hasNext()){
										ModelPart var13 = ((ModelPart)(var12.next()));
										var13.resetPose();
										this.applySkinOverlay(var1,var13,var4,var5,var6,var8.getRGB());
										var4.pushPose();
										var13.render(var4,var5.getBuffer(this.getSkinRenderType(var1)),var6,OverlayTexture.NO_OVERLAY,Color.WHITE.getRGB());
										var4.popPose();
										continue;
									}else{
										break;
									}
								} while (true);
							}else{
								return;
							}
						}
					}
				}
			}
			var4.popPose();
			super.render(var1,var2,var3,var4,var5,var6);
			return;
		}
	}
	private boolean shouldApplyBasicHakiOverlay(T var1) {
		int var2 = ((var1.isAffectedByHardening()) && (HakiHelper.hasHardeningActive(var1.getOwner(),false,false))) ? (1) : (0);
		int var3 = ((var1.isAffectedByImbuing()) && (HakiHelper.isProjectileBusoshokuImbued(var1))) ? (1) : (0);
		return ((var2) != (0)) || ((var3) != (0));
	}
	private boolean shouldApplyAdvancedHakiOverlay(T var1) {
		int var2 = ((var1.isAffectedByHardening()) && (HakiHelper.hasAdvancedBusoActive(var1.getOwner()))) ? (1) : (0);
		int var3 = ((var1.isAffectedByImbuing()) && (HakiHelper.isProjectileBusoshokuShrouded(var1))) ? (1) : (0);
		return ((var2) != (0)) || ((var3) != (0));
	}
	public void applyModelOverlay(T var1, M var2, PoseStack var3, MultiBufferSource var4, int var5) {
		if ((var1.getOwner()) != (null)){
			if (this.shouldApplyBasicHakiOverlay(var1)){
				var3.pushPose();
				var2.renderToBuffer(var3,var4.getBuffer(RenderType.entityTranslucent(ModResources.BUSOSHOKU_HAKI_ARM)),var5,OverlayTexture.NO_OVERLAY,DEFAULT_COLOR);
				var3.popPose();
			}
			if (this.shouldApplyAdvancedHakiOverlay(var1)){
				var3.pushPose();
				Color var6 = WyHelper.intToRGB(HakiHelper.getHaoshokuColour(var1.getOwner()),102);
				float var7 = 1.1F;
				var3.scale(var7,var7,var7);
				var2.renderToBuffer(var3,var4.getBuffer(ModRenderTypes.TRANSPARENT_COLOR),var5,OverlayTexture.NO_OVERLAY,var6.getRGB());
				var3.popPose();
			}else{
				return;
			}
		}
	}
	public void applySkinOverlay(T var1, ModelPart var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
		ResourceLocation var7 = this.getTextureLocation(var1);
		if ((var7) != (null)){
			var3.pushPose();
			VertexConsumer var8 = var4.getBuffer(RenderType.entityTranslucent(var7));
			var2.render(var3,var8,var5,OverlayTexture.NO_OVERLAY,var6);
			var3.popPose();
		}
		if ((var1.getOwner()) != (null)){
			if (this.shouldApplyBasicHakiOverlay(var1)){
				var3.pushPose();
				VertexConsumer var8_1 = var4.getBuffer(RenderType.entityTranslucent(ModResources.BUSOSHOKU_HAKI_ARM));
				var2.render(var3,var8_1,var5,OverlayTexture.NO_OVERLAY,DEFAULT_COLOR);
				var3.popPose();
			}
			if (this.shouldApplyAdvancedHakiOverlay(var1)){
				var3.pushPose();
				Color var8_2 = WyHelper.intToRGB(HakiHelper.getHaoshokuColour(var1.getOwner()),102);
				VertexConsumer var9 = var4.getBuffer(ModRenderTypes.TRANSPARENT_COLOR);
				float var10 = 1.1F;
				var2.zScale = var10;
				var2.yScale = var10;
				var2.xScale = var10;
				var2.render(var3,var9,var5,OverlayTexture.NO_OVERLAY,var8_2.getRGB());
				var3.popPose();
			}else{
				return;
			}
		}
	}
	public ResourceLocation getTextureLocation(NuProjectileEntity var1) {
		return this.texture;
	}
	@Nullable()
	public @Nullable() ResourceLocation getSkinTextureLocation(NuProjectileEntity var1) {
		if ((var1.getOwner()) != (null)){
			LivingEntity var2 = var1.getOwner();
			if (var2 instanceof AbstractClientPlayer){
				return ((AbstractClientPlayer)(var2)).getSkin().texture();
			}
		}
		return DefaultPlayerSkin.get(var1.getUUID()).texture();
	}
	@Nullable()
	public @Nullable() RenderType getRenderType(NuProjectileEntity var1) {
		RenderType var2 = null;
		ResourceLocation var3 = this.getTextureLocation(var1);
		if ((var3) == (null)){
			var2 = (this.isGlowing) ? (ModRenderTypes.ENERGY) : (ModRenderTypes.TRANSPARENT_COLOR);
		}else{
			var2 = RenderType.entityTranslucent(var3);
		}
		return var2;
	}
	@Nullable()
	public @Nullable() RenderType getSkinRenderType(NuProjectileEntity var1) {
		return RenderType.entityTranslucent(this.getSkinTextureLocation(var1));
	}
	private void renderDebugBox(PoseStack var1, VertexConsumer var2, T var3) {
		LevelRenderer.renderLineBox(var1,var2,new AABB((-(var3.getBlockCollisionBox().getXsize())) / (2D),(-(var3.getBlockCollisionBox().getYsize())) / (2D),(-(var3.getBlockCollisionBox().getZsize())) / (2D),(var3.getBlockCollisionBox().getXsize()) / (2D),(var3.getBlockCollisionBox().getYsize()) / (2D),(var3.getBlockCollisionBox().getZsize()) / (2D)),1F,0F,0F,1F);
	}
	static  {
		DEFAULT_COLOR = WyHelper.packColor(1F,1F,1F,0.8F);
	}
}