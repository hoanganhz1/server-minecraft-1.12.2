package xyz.pixelatedw.mineminenomi.renderers.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModLayers;
import xyz.pixelatedw.mineminenomi.init.ModMorphs$Client;
import xyz.pixelatedw.mineminenomi.init.ModMorphs$Client$MorphArmorEntry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.morphs.armors.MorphArmorModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.ArrowMorphLayer;

public class MorphRenderer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel & HeadedModel> extends LivingEntityRenderer<T, M> {
	// Fields
	final MorphInfo info;
	private EntityRenderer<? super T> originalRenderer;

	public MorphRenderer(EntityRendererProvider.Context var1, MorphInfo var2, M var3) {
		super(var1,var3,var2.getShadowSize());
		this.info = var2;
		ModMorphs$Client$MorphArmorEntry var4 = ((ModMorphs$Client$MorphArmorEntry)(ModMorphs$Client.MORPH_ARMORS.get(var2)));
		if ((var4) != (null)){
			MorphArmorModel var5 = ((MorphArmorModel)(var4.inner()));
			MorphArmorModel var6 = ((MorphArmorModel)(var4.outer()));
			this.addLayer((RenderLayer)(new HumanoidArmorLayer((RenderLayerParent)(this),(HumanoidModel)(var5),(HumanoidModel)(var6),var1.getModelManager())));
		}
		this.addLayer((RenderLayer)(new ItemInHandLayer((RenderLayerParent)(this),var1.getItemInHandRenderer())));
		this.addLayer((RenderLayer)(new ArrowMorphLayer(var1,(LivingEntityRenderer)(this))));
		this.addLayer((RenderLayer)(new ElytraLayer((RenderLayerParent)(this),var1.getModelSet())));
		ModLayers.addExtraFeatureLayers(var1,(LivingEntityRenderer)(this));
		ModLayers.addGenericLayers(var1,(LivingEntityRenderer)(this));
		ModLayers.addAbilityLayers(var1,(LivingEntityRenderer)(this));
		this.info.addLayers(var1,(LivingEntityRenderer)(this));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		this.setModelProperties(var1);
		var4.pushPose();
		this.model.attackTime = this.getAttackAnim(var1,var3);
		boolean var7 = (var1.isPassenger()) && (((var1.getVehicle()) != (null)) && (var1.getVehicle().shouldRiderSit()));
		this.model.riding = var7;
		this.model.young = var1.isBaby();
		float var8 = Mth.rotLerp(var3,var1.yBodyRotO,var1.yBodyRot);
		float var9 = Mth.rotLerp(var3,var1.yHeadRotO,var1.yHeadRot);
		float var10 = (var9) - (var8);
		if ((var7) && (var1.getVehicle() instanceof LivingEntity)){
			LivingEntity var11 = ((LivingEntity)(var1.getVehicle()));
			float var12 = Mth.rotLerp(var3,var11.yBodyRotO,var11.yBodyRot);
			float var13 = (var9) - (var12);
			float var14 = Mth.wrapDegrees(var13);
			if ((var14) < (-85F)){
				var14 = -85F;
			}
			if ((var14) >= (85F)){
				var14 = 85F;
			}
			var8 = (var9) - (var14);
			if (((var14) * (var14)) > (2500F)){
				var8 = (var8) + ((var14) * (0.2F));
			}
			var10 = (var9) - (var8);
		}
		float var11_1 = Mth.lerp(var3,var1.xRotO,var1.getXRot());
		if ((var1.getPose()) == (Pose.SLEEPING)){
			Direction var12_1 = var1.getBedOrientation();
			if ((var12_1) != (null)){
				float var13_1 = (var1.getEyeHeight(Pose.STANDING)) - (0.1F);
				var4.translate((double)(((float)(-(var12_1.getStepX()))) * (var13_1)),0D,(double)(((float)(-(var12_1.getStepZ()))) * (var13_1)));
			}
		}
		float var12_2 = var1.getScale();
		var4.scale(var12_2,var12_2,var12_2);
		float var13_1 = this.getBob(var1,var3);
		this.setupRotations(var1,var4,var13_1,var8,var3,var12_2);
		var4.scale(-1F,-1F,1F);
		this.scale(var1,var4,var3);
		var4.translate(0D,-1.5010000467300415D,0D);
		float var14_1 = 0F;
		float var15 = 0F;
		if ((!(var7)) && (var1.isAlive())){
			var14_1 = var1.walkAnimation.speed(var3);
			var15 = var1.walkAnimation.position(var3);
			if (var1.isBaby()){
				var15 = (var15) * (3F);
			}
			if ((var14_1) > (1F)){
				var14_1 = 1F;
			}
		}
		this.renderModel(var1,var4,var6,var5,var15,var14_1,var3,var13_1,var10,var11_1);
		var4.popPose();
		RenderNameTagEvent var16 = ((RenderNameTagEvent)(NeoForge.EVENT_BUS.post((Event)(new RenderNameTagEvent(var1,var1.getDisplayName(),(EntityRenderer)(this),var4,var5,var6,var3)))));
		if (((var16.canRender()) != (TriState.FALSE)) && (!(((var16.canRender()) != (TriState.TRUE)) && (!(this.shouldShowName(var1)))))){
			this.renderNameTag(var1,var16.getContent(),var4,var5,var6,var3);
		}
	}
	protected void renderModel(T var1, PoseStack var2, int var3, MultiBufferSource var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		this.getModel().prepareMobModel(var1,var5,var6,var7);
		if (!(var1.getActiveEffects().stream().anyMatch((Predicate)(((Predicate<MobEffectInstance>)((l0) -> {
			Holder lv1_1 = l0.getEffect();
			int lv1_2 = 0;
			if (lv1_1 instanceof IBindBodyEffect){
				IBindBodyEffect lv1_3 = ((IBindBodyEffect)(lv1_1));
				if (lv1_3.isBlockingRotation()){
					lv1_2 = 1;
				}else{
					lv1_2 = 0;
				}
			}else{
				lv1_2 = 0;
			}
			return (((lv1_2) & 1) != 0);
		})))))){
			this.getModel().setupAnim(var1,var5,var6,var8,var9,var10);
			Animation.animationAngles(var1,this.model,var5,var6,var8,var9,var10);
		}
		Animation.animationPostAngles(var1,this.model,var2,var4,var3,var7,var5,var6,var8,var9,var10);
		boolean var11 = this.isBodyVisible(var1);
		int var12 = ((!(var11)) && (!(var1.isInvisibleTo((Player)(Minecraft.getInstance().player))))) ? (1) : (0);
		RenderType var13 = RenderType.entityTranslucent(this.getTextureLocation(var1));
		if (((var13) != (null)) && (var11)){
			VertexConsumer var14 = var4.getBuffer(var13);
			int var15 = getOverlayCoords(var1,this.getWhiteOverlayProgress(var1,var7));
			int var16 = WyHelper.packColor(1F,1F,1F,((var12) != (0)) ? (0.15F) : (1F));
			this.model.renderToBuffer(var2,var14,var3,var15,var16);
		}
		if (!(var1.isSpectator())){
			Iterator var14_1 = this.layers.iterator();
			do{
				if (var14_1.hasNext()){
					RenderLayer var15_1 = ((RenderLayer)(var14_1.next()));
					if (this.info.shouldRenderLayer(var15_1,var1,var2,var7)){
						var15_1.render(var2,var4,var3,var1,var5,var6,var7,var8,var9,var10);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	public void renderFirstPersonLimb(T var1, ItemStack var2, PoseStack var3, MultiBufferSource var4, int var5, HumanoidArm var6, Optional<AbilityOverlay> var7, float var8, float var9, boolean var10) {
		EntityModel var11 = this.getModel();
		if (var11 instanceof PlayerModel){
			PlayerModel var12 = ((PlayerModel)(var11));
			this.setModelProperties(var1);
			var12.attackTime = 0F;
			var12.crouching = false;
			var12.swimAmount = 0F;
			var12.setupAnim(var1,0F,0F,0F,0F,0F);
			ModelPart var13 = null;
			ModelPart var14 = null;
			if ((var6) == (HumanoidArm.RIGHT)){
				var13 = var12.rightArm;
				var14 = var12.rightSleeve;
			}else{
				var13 = var12.leftArm;
				var14 = var12.leftSleeve;
			}
			if (var1 instanceof AbstractClientPlayer){
				AbstractClientPlayer var15 = ((AbstractClientPlayer)(var1));
				var13.xRot = 0F;
				var13.render(var3,var4.getBuffer(RenderType.entitySolid(var15.getSkin().texture())),var5,OverlayTexture.NO_OVERLAY);
				var14.xRot = 0F;
				var14.render(var3,var4.getBuffer(RenderType.entityTranslucent(var15.getSkin().texture())),var5,OverlayTexture.NO_OVERLAY);
			}else{
				return;
			}
		}else{
			EntityModel var12_1 = this.getModel();
			if (var12_1 instanceof HumanoidMorphModel){
				HumanoidMorphModel var13_1 = ((HumanoidMorphModel)(var12_1));
				VertexConsumer var14_1 = var4.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(var1)));
				float var15_1 = 1F;
				float var16 = 1F;
				float var17 = 1F;
				float var18 = 1F;
				var3.pushPose();
				var13_1.renderFirstPersonArm(var3,var14_1,var5,OverlayTexture.NO_OVERLAY,var15_1,var16,var17,var18,var6,var10);
				var3.popPose();
				if ((var7.isPresent()) && ((((AbilityOverlay)(var7.get())).getTexture()) != (null))){
					VertexConsumer var19 = var4.getBuffer(RenderType.entityTranslucent(((AbilityOverlay)(var7.get())).getTexture()));
					Color var20 = ((AbilityOverlay)(var7.get())).getColor();
					var15_1 = ((float)(var20.getRed())) / (255F);
					var16 = ((float)(var20.getGreen())) / (255F);
					var17 = ((float)(var20.getBlue())) / (255F);
					var18 = ((float)(var20.getAlpha())) / (255F);
					var3.pushPose();
					var3.scale(1.02F,1.02F,1.02F);
					var3.translate(-0F,-0.01F,-0.01F);
					var13_1.renderFirstPersonArm(var3,var19,var5,OverlayTexture.NO_OVERLAY,var15_1,var16,var17,var18,var6,var10);
					var3.popPose();
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	private void setModelProperties(T var1) {
		EntityModel var2 = this.getModel();
		if (var2 instanceof HumanoidModel){
			HumanoidModel var3 = ((HumanoidModel)(var2));
			if (var1.isSpectator()){
				var3.setAllVisible(false);
				var3.head.visible = true;
				var3.hat.visible = true;
			}else{
				var3.setAllVisible(true);
				var3.crouching = var1.isCrouching();
			}
		}
	}
	public ResourceLocation getTextureLocation(T var1) {
		ResourceLocation var2 = this.info.getTexture(var1);
		if ((var2) != (null)){
			return var2;
		}else{
			return ModResources.NULL_ENTITY_TEXTURE;
		}
	}
	public void removeLayer(Class<?> var1) {
		this.layers.removeIf((Predicate)(((Predicate<RenderLayer>)((l0) -> {
			return ((java.lang.Object)(var1)).equals((Object)(l0.getClass()));
		}))));
	}
	public void setCullingState(boolean var1) {
	}
	public float getShadowSize() {
		return this.info.getShadowSize();
	}
	public void setOriginalRenderer(EntityRenderer<? super T> var1) {
		this.originalRenderer = var1;
	}
	public EntityRenderer<? super T> getOriginalRenderer() {
		return (EntityRenderer<? super T>) (EntityRenderer) (this.originalRenderer);
	}
}