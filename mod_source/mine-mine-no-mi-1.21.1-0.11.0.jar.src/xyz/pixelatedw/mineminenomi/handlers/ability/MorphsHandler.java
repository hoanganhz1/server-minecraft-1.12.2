package xyz.pixelatedw.mineminenomi.handlers.ability;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$RenderType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.events.TriEventResult;
import xyz.pixelatedw.mineminenomi.api.helpers.MorphsHelper$Client;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;
import xyz.pixelatedw.mineminenomi.api.morph.IFirstPersonHandReplacer;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModMorphs$Client;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class MorphsHandler {
	public static void renderHandItemForPartials(LivingEntity var0, MorphInfo var1, PoseStack var2, MultiBufferSource var3, int var4, ItemInHandLayer<LivingEntity, ? extends EntityModel<LivingEntity>> var5) {
		EntityModel var6 = ((EntityModel)(ModMorphs$Client.PARTIAL_MORPHS_RENDERERS.get(var1)));
		int var7 = ((var0.getMainArm()) == (HumanoidArm.RIGHT)) ? (1) : (0);
		ItemStack var8 = ((var7) != (0)) ? (var0.getOffhandItem()) : ((ItemStack)(var0.getMainHandItem()));
		ItemStack var9 = ((var7) != (0)) ? (var0.getMainHandItem()) : ((ItemStack)(var0.getOffhandItem()));
		if ((var8.isEmpty()) && (var9.isEmpty())){

		}else{
			var2.pushPose();
			renderArmWithItem(var5,var6,var0,var9,ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,HumanoidArm.RIGHT,var2,var3,var4);
			renderArmWithItem(var5,var6,var0,var8,ItemDisplayContext.THIRD_PERSON_LEFT_HAND,HumanoidArm.LEFT,var2,var3,var4);
			var2.popPose();
		}
	}
	protected static void renderArmWithItem(ItemInHandLayer<LivingEntity, ? extends EntityModel<LivingEntity>> var0, EntityModel<? extends LivingEntity> var1, LivingEntity var2, ItemStack var3, ItemDisplayContext var4, HumanoidArm var5, PoseStack var6, MultiBufferSource var7, int var8) {
		if (!(var3.isEmpty())){
			var6.pushPose();
			if (var1 instanceof HumanoidMorphModel){
				if (!(((HumanoidMorphModel)(var1)).renderItemInHand(var2,var5,var6))){
					var6.popPose();
					return;
				}
			}else{
				((ArmedModel)(var0.getParentModel())).translateToHand(var5,var6);
			}
			var6.mulPose(Axis.XP.rotationDegrees(-90F));
			var6.mulPose(Axis.YP.rotationDegrees(180F));
			int var9 = ((var5) == (HumanoidArm.LEFT)) ? (1) : (0);
			var6.translate(((float)(((var9) != (0)) ? (-1) : (1))) / (16F),0.125F,-0.625F);
			Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(var2,var3,var4,(var9) != (0),var6,var7,var8);
			var6.popPose();
		}
	}
	public static <T extends LivingEntity, M extends EntityModel<T>> void preRenderHook(T var0, M var1, LivingEntityRenderer<T, M> var2, PoseStack var3, float var4, float var5, float var6) {
		DevilFruitData var7 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		if ((var7) == (null)){
			return;
		}else{
			Optional var8 = var7.getCurrentMorph();
			if (var8.isPresent()){
				((MorphInfo)(var8.get())).preRenderCallback(var0,var2,var3,var6);
			}
			Iterator var9 = var7.getActiveMorphs().iterator();
			do{
				if (var9.hasNext()){
					MorphInfo var10 = ((MorphInfo)(var9.next()));
					if (!(var10.isPartial())){
						continue;
					}else{
						var10.preRenderCallback(var0,var2,var3,var6);
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static <T extends LivingEntity, M extends EntityModel<T>> void postRenderHook(T var0, M var1, LivingEntityRenderer<T, M> var2, PoseStack var3, float var4, float var5, float var6) {
		DevilFruitData var7 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		if ((var7) == (null)){
			return;
		}else{
			Optional var8 = var7.getCurrentMorph();
			if (var8.isPresent()){
				((MorphInfo)(var8.get())).postRenderCallback(var0,var2,var3,var6);
			}
			Iterator var9 = var7.getActiveMorphs().iterator();
			do{
				if (var9.hasNext()){
					MorphInfo var10 = ((MorphInfo)(var9.next()));
					if (!(var10.isPartial())){
						continue;
					}else{
						var10.postRenderCallback(var0,var2,var3,var6);
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static <T extends LivingEntity> void armorVisibilityHook(T var0, Model var1, EquipmentSlot var2) {
		DevilFruitData var3 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			Optional var4 = var3.getCurrentMorph();
			if (var4.isPresent()){
				((MorphInfo)(var4.get())).armorVisibilityCallback(var0,var1,var2);
			}
			Iterator var5 = var3.getActiveMorphs().iterator();
			do{
				if (var5.hasNext()){
					MorphInfo var6 = ((MorphInfo)(var5.next()));
					if (!(var6.isPartial())){
						continue;
					}else{
						var6.armorVisibilityCallback(var0,var1,var2);
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static TriEventResult renderLimbWithOverlay(PoseStack var0, MultiBufferSource var1, ItemStack var2, int var3, float var4, float var5, LocalPlayer var6) {
		EntityRenderer var7 = MorphsHelper$Client.getMorphRenderer(var6);
		Optional var8 = Optional.empty();
		AbilityData var9 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var6)))).orElse((Object)(null))));
		if ((var9) != (null)){
			Iterator var10 = var9.getEquippedAbilities().iterator();
			do{
				if (var10.hasNext()){
					Optional var11 = ((IAbility)(var10.next())).getComponent(((AbilityComponentKey)(ModAbilityComponents.SKIN_OVERLAY.get())));
					if (var11.isPresent()){
						Optional var12 = ((SkinOverlayComponent)(var11.get())).getShownOverlay(AbilityOverlay$OverlayPart.values());
						if (var12.isPresent()){
							var8 = var12;
							continue;
						}else{
							continue;
						}
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		ResourceLocation var10_1 = null;
		int var11_1 = Color.WHITE.getRGB();
		if (var8.isPresent()){
			var10_1 = ((AbilityOverlay)(var8.get())).getTexture();
			var11_1 = ((AbilityOverlay)(var8.get())).getColor().getRGB();
		}
		int var12_1 = ((var6.getMainArm()) != (HumanoidArm.LEFT)) ? (1) : (0);
		boolean var13 = ((Boolean)(EntityStatsData.get((LivingEntity)(var6)).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isBlackLeg)))).orElse(Boolean.valueOf(false)))).booleanValue();
		var0.pushPose();
		DevilFruitData var14 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var6)))).orElse((Object)(null))));
		if (((var14) != (null)) && ((var14.getActiveMorphs().size()) > (0))){
			MorphInfo[] var15 = ((MorphInfo[])(var14.getActiveMorphs().toArray(new MorphInfo[0])));
			int var16 = (var14.getActiveMorphs().size()) - (1);
			do{
				if ((var16) >= (0)){
					MorphInfo var17 = var15[var16];
					EntityModel var18 = ((EntityModel)(ModMorphs$Client.PARTIAL_MORPHS_RENDERERS.get(var17)));
					if (((var18) != (null)) && (var18 instanceof IFirstPersonHandReplacer)){
						IFirstPersonHandReplacer var19 = ((IFirstPersonHandReplacer)(var18));
						VertexConsumer var20 = var1.getBuffer(RenderType.entityTranslucent(var17.getTexture((LivingEntity)(var6))));
						translateLimb(var0,var4,var5,(var12_1) != (0),var13);
						var0.pushPose();
						boolean var21 = var19.renderFirstPersonArm(var0,var20,var3,OverlayTexture.NO_OVERLAY,1F,1F,1F,1F,var6.getMainArm(),var13);
						var0.popPose();
						if (var8.isPresent()){
							switch (MorphsHandler$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$AbilityOverlay$RenderType[((AbilityOverlay)(var8.get())).getRenderType().ordinal()]){
							default:
								throw new MatchException((String)(null),(Throwable)(null));
							case 1:
								if ((var10_1) != (null)){

								}
							case 2:
								VertexConsumer var22 = var1.getBuffer(ModRenderTypes.ENERGY);
								var0.pushPose();
								var19.renderFirstPersonArm(var0,var22,var3,OverlayTexture.NO_OVERLAY,var11_1,var6.getMainArm(),var13);
								var0.popPose();
							}
						}
						var0.popPose();
						return (var21) ? (TriEventResult.DENY) : (TriEventResult.DEFAULT);
					}else{
						if (var7 instanceof MorphRenderer){
							MorphRenderer var19_1 = ((MorphRenderer)(var7));
							translateLimb(var0,var4,var5,(var12_1) != (0),var13);
							var19_1.renderFirstPersonLimb(var6,var2,var0,var1,var3,var6.getMainArm(),var8,var4,var5,var13);
							var0.popPose();
							return TriEventResult.DENY;
						}else{
							var16--;
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
		}
		if ((!(var8.isPresent())) && (!(var13))){
			var0.popPose();
			return TriEventResult.DEFAULT;
		}else{
			EntityRenderer var15_1 = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer((Entity)(var6));
			if (var15_1 instanceof PlayerRenderer){
				PlayerModel var16_1 = ((PlayerModel)(((PlayerRenderer)(var15_1)).getModel()));
				RendererHelper.resetModelToDefaultPivots(var16_1);
				translateLimb(var0,var4,var5,(var12_1) != (0),var13);
				ModelPart var17_1 = var16_1.rightArm;
				ModelPart var18_1 = var16_1.rightSleeve;
				VertexConsumer var19_2 = var1.getBuffer(RenderType.entityCutout(var15_1.getTextureLocation((Entity)(var6))));
				if (var13){
					var17_1 = ((var12_1) != (0)) ? (var16_1.rightLeg) : (var16_1.leftLeg);
					var18_1 = ((var12_1) != (0)) ? (var16_1.rightPants) : (var16_1.leftPants);
				}else{
					var17_1 = ((var12_1) != (0)) ? (var16_1.rightArm) : (var16_1.leftArm);
					var18_1 = ((var12_1) != (0)) ? (var16_1.rightSleeve) : (var16_1.leftSleeve);
				}
				var17_1.zRot = 0.1F;
				var18_1.zRot = 0.1F;
				var17_1.y = (var17_1.y) - (0.5F);
				var18_1.y = (var18_1.y) - (0.5F);
				var0.pushPose();
				var17_1.render(var0,var19_2,var3,OverlayTexture.NO_OVERLAY,var11_1);
				var18_1.render(var0,var19_2,var3,OverlayTexture.NO_OVERLAY,var11_1);
				if (var8.isPresent()){
					renderLimbOverlay(var0,var1,var17_1,((AbilityOverlay)(var8.get())),var10_1,var3,var11_1,var13);
				}
				var0.popPose();
				var0.popPose();
				return TriEventResult.DENY;
			}else{
				return TriEventResult.DEFAULT;
			}
		}
	}
	private static void renderLimbOverlay(PoseStack var0, MultiBufferSource var1, @Nonnull() ModelPart var2, @Nonnull() AbilityOverlay var3, @Nullable() ResourceLocation var4, int var5, int var6, boolean var7) {
		switch (MorphsHandler$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$AbilityOverlay$RenderType[var3.getRenderType().ordinal()]){
		default:
			throw new MatchException((String)(null),(Throwable)(null));
		case 1:
			if ((var4) != (null)){

			}
		case 2:
			VertexConsumer var8 = var1.getBuffer(ModRenderTypes.ENERGY);
			if (var7){
				var0.translate(0F,-0.01F,0F);
			}
			var2.xScale = 1.17F;
			var2.yScale = 1.05F;
			var2.zScale = 1.17F;
			var2.render(var0,var8,var5,OverlayTexture.NO_OVERLAY,var6);
			return;
		}
	}
	private static void translateLimb(PoseStack var0, float var1, float var2, boolean var3, boolean var4) {
		float var10 = 0.0F;
		float var6 = 0.0F;
		float var7 = 0.0F;
		float var8 = 0.0F;
		float var9 = 0.0F;
		float var5 = (var3) ? (1F) : (-1F);
		if (var4){
			var6 = (Mth.sqrt(var1)) / (1.55F);
			var7 = (-0.3F) * (Mth.sin((var6) * (3.1415927F)));
			var8 = (0.4F) * (Mth.sin((var6) * (6.2831855F)));
			var9 = (-0.4F) * (Mth.sin((var1) * (3.1415927F)));
			var0.translate((var5) * ((var7) + (0.65F)),((var8) + (-0.05F)) + ((var2) * (-0.6F)),(var9) + (-0.4F));
			var0.mulPose(Axis.ZN.rotationDegrees((var5) * (85F)));
			var10 = Mth.sin((var6) * (3.1415927F));
			var0.mulPose(Axis.YP.rotationDegrees(((var5) * (var10)) * (70F)));
			var0.mulPose(Axis.XP.rotationDegrees((((var5) * (var10)) * (-100F)) * (var5)));
		}else{
			var6 = Mth.sqrt(var1);
			var7 = (-0.3F) * (Mth.sin((var6) * (3.1415927F)));
			var8 = (0.4F) * (Mth.sin((var6) * (6.2831855F)));
			var9 = (-0.4F) * (Mth.sin((var1) * (3.1415927F)));
			var0.translate((var5) * ((var7) + (0.64F)),((var8) + (-0.6F)) + ((var2) * (-0.6F)),(var9) + (-0.72F));
			var0.mulPose(Axis.YP.rotationDegrees((var5) * (45F)));
			var10 = Mth.sin(((var1) * (var1)) * (3.1415927F));
			float var11 = Mth.sin((var6) * (3.1415927F));
			var0.mulPose(Axis.YP.rotationDegrees(((var5) * (var11)) * (70F)));
			var0.mulPose(Axis.ZP.rotationDegrees(((var5) * (var10)) * (-20F)));
		}
		var0.translate((var5) * (-1F),3.6F,3.5F);
		var0.mulPose(Axis.ZP.rotationDegrees((var5) * (120F)));
		var0.mulPose(Axis.XP.rotationDegrees(200F));
		var0.mulPose(Axis.YP.rotationDegrees((var5) * (-135F)));
		var0.translate((var5) * (5.6F),0F,0F);
	}
	public static double getMorphCameraZoom(Player var0) {
		DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return 0D;
		}else{
			Optional var2 = var1.getCurrentMorph();
			if (var2.isEmpty()){
				Iterator var3 = var1.getActiveMorphs().iterator();
				do{
					if (var3.hasNext()){
						MorphInfo var4 = ((MorphInfo)(var3.next()));
						if (!(var4.isPartial())){
							continue;
						}else{
							return var4.getCameraZoom((LivingEntity)(var0));
						}
					}else{
						break;
					}
				} while (true);
				return 0D;
			}else{
				if (!(var1.hasMorphInQueue(((MorphInfo)(var2.get()))))){
					return 0D;
				}else{
					return ((MorphInfo)(var2.get())).getCameraZoom((LivingEntity)(var0));
				}
			}
		}
	}
	public static void updateMorphCameraOffset(Player var0, Vec3 var1) {
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			Optional var3 = var2.getCurrentMorph();
			double var4 = 0D;
			if (var3.isEmpty()){
				boolean var5 = false;
				Iterator var6 = var2.getActiveMorphs().iterator();
				do{
					if (var6.hasNext()){
						MorphInfo var7 = ((MorphInfo)(var6.next()));
						if (!(var7.isPartial())){
							continue;
						}else{
							var5 = true;
							var4 = var7.getCameraHeight((LivingEntity)(var0));
							break;
						}
					}else{
						break;
					}
				} while (true);
				if (!(var5)){
					return;
				}
			}else{
				if (!(var2.hasMorphInQueue(((MorphInfo)(var3.get()))))){
					return;
				}else{
					var4 = ((MorphInfo)(var3.get())).getCameraHeight((LivingEntity)(var0));
				}
			}
			var1.add(0D,var4,0D);
			return;
		}
	}
}