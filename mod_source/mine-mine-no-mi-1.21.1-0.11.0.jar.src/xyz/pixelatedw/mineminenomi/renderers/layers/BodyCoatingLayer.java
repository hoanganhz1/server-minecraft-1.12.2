package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.awt.Color;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFourthAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$RenderType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MorphsHelper$Client;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.morph.RacialRenderFeature;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModMorphs$Client;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class BodyCoatingLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public BodyCoatingLayer(RenderLayerParent<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		EntityStatsData var11 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var4))).orElse((Object)(null))));
		AbilityData var12 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var4))).orElse((Object)(null))));
		DevilFruitData var13 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var4))).orElse((Object)(null))));
		if (((var11) != (null)) && (((var12) != (null)) && ((var13) != (null)))){
			GearFourthAbility var14 = ((GearFourthAbility)(var12.getEquippedAbility(((AbilityCore)(GearFourthAbility.INSTANCE.get())))));
			int var15 = (((var14) != (null)) && (var14.isContinuous())) ? (1) : (0);
			VertexConsumer var16 = null;
			Optional var17 = Optional.empty();
			float var18 = 1F;
			float var19 = 1F;
			float var20 = 1F;
			float var21 = 1F;
			Iterator var22 = var12.getEquippedAbilities().iterator();
			LOOP_1:
			do{
				if (var22.hasNext()){
					IAbility var23 = ((IAbility)(var22.next()));
					Optional var24 = var23.getComponent(((AbilityComponentKey)(ModAbilityComponents.SKIN_OVERLAY.get())));
					if (var24.isEmpty()){
						continue;
					}else{
						SkinOverlayComponent var25 = ((SkinOverlayComponent)(var24.get()));
						AbilityOverlay$OverlayPart[] var26 = new AbilityOverlay$OverlayPart[]{AbilityOverlay$OverlayPart.LIMB,AbilityOverlay$OverlayPart.LEG,AbilityOverlay$OverlayPart.ARM};
						Optional var27 = var25.getShownOverlay(var26);
						if ((var27.isPresent()) && (this.getParentModel() instanceof ArmedModel)){
							var18 = ((float)(((AbilityOverlay)(var27.get())).getColor().getRed())) / (255F);
							var19 = ((float)(((AbilityOverlay)(var27.get())).getColor().getGreen())) / (255F);
							var20 = ((float)(((AbilityOverlay)(var27.get())).getColor().getBlue())) / (255F);
							var21 = ((float)(((AbilityOverlay)(var27.get())).getColor().getAlpha())) / (255F);
							if ((((AbilityOverlay)(var27.get())).getTexture()) != (null)){
								var16 = var2.getBuffer(ModRenderTypes.getAbilityHand(((AbilityOverlay)(var27.get())).getTexture()));
							}else{
								var16 = (((AbilityOverlay)(var27.get())).getRenderType().equals(AbilityOverlay$RenderType.ENERGY)) ? (var2.getBuffer(ModRenderTypes.ENERGY)) : ((VertexConsumer)(var2.getBuffer(ModRenderTypes.TRANSPARENT_COLOR)));
							}
							int var28 = ((var4.getMainArm()) == (HumanoidArm.LEFT)) ? (1) : (0);
							ModelPart var29 = null;
							int var30 = ((((AbilityOverlay)(var27.get())).getOverlayPart().equals(AbilityOverlay$OverlayPart.LEG)) || ((var11.isBlackLeg()) && (((AbilityOverlay)(var27.get())).getOverlayPart().equals(AbilityOverlay$OverlayPart.LIMB)))) ? (1) : (0);
							int var31 = ((((AbilityOverlay)(var27.get())).getOverlayPart().equals(AbilityOverlay$OverlayPart.ARM)) || ((!(var11.isBlackLeg())) && (((AbilityOverlay)(var27.get())).getOverlayPart().equals(AbilityOverlay$OverlayPart.LIMB)))) ? (1) : (0);
							if ((var30) != (0)){
								EntityRenderer var32 = MorphsHelper$Client.getMorphRenderer(var4);
								if (((var32) != (null)) && (var32 instanceof MorphRenderer)){
									EntityModel var33 = ((MorphRenderer)(var32)).getModel();
									if (var33 instanceof HumanoidMorphModel){
										HumanoidMorphModel var34 = ((HumanoidMorphModel)(var33));
										var29 = ((var28) != (0)) ? (var34.leftLeg) : (var34.rightLeg);
									}
								}else{
									EntityModel var33_1 = this.getParentModel();
									if (var33_1 instanceof PlayerModel){
										PlayerModel var34_1 = ((PlayerModel)(var33_1));
										var29 = ((var28) != (0)) ? (var34_1.leftPants) : (var34_1.rightPants);
									}else{
										EntityModel var34_2 = this.getParentModel();
										if (var34_2 instanceof HumanoidModel){
											HumanoidModel var35 = ((HumanoidModel)(var34_2));
											var29 = ((var28) != (0)) ? (var35.leftLeg) : (var35.rightLeg);
										}else{
											EntityModel var35_1 = this.getParentModel();
											if (var35_1 instanceof DugongModel){
												DugongModel var36 = ((DugongModel)(var35_1));
												var29 = var36.tailBase;
											}
										}
									}
								}
							}else{
								if ((var31) != (0)){
									EntityRenderer var32_1 = MorphsHelper$Client.getMorphRenderer(var4);
									if (((var32_1) != (null)) && (var32_1 instanceof MorphRenderer)){
										EntityModel var33_2 = ((MorphRenderer)(var32_1)).getModel();
										if (var33_2 instanceof HumanoidMorphModel){
											HumanoidMorphModel var34_3 = ((HumanoidMorphModel)(var33_2));
											var29 = ((var28) != (0)) ? (var34_3.leftArm) : (var34_3.rightArm);
										}
									}else{
										EntityModel var33_3 = this.getParentModel();
										if (var33_3 instanceof PlayerModel){
											PlayerModel var34_4 = ((PlayerModel)(var33_3));
											var29 = ((var28) != (0)) ? (var34_4.leftSleeve) : (var34_4.rightSleeve);
										}else{
											EntityModel var34_5 = this.getParentModel();
											if (var34_5 instanceof HumanoidModel){
												HumanoidModel var35_2 = ((HumanoidModel)(var34_5));
												var29 = ((var28) != (0)) ? (var35_2.leftArm) : (var35_2.rightArm);
											}else{
												EntityModel var35_3 = this.getParentModel();
												if (var35_3 instanceof DugongModel){
													DugongModel var36_1 = ((DugongModel)(var35_3));
													var29 = ((var28) != (0)) ? (var36_1.leftArm) : (var36_1.rightArm);
												}
											}
										}
									}
								}
							}
							if ((var29) != (null)){
								var29.render(var1,var16,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(var18,var19,var20,var21));
							}
						}
						SkinOverlayComponent var28_1 = ((SkinOverlayComponent)(var24.get()));
						AbilityOverlay$OverlayPart[] var29_1 = new AbilityOverlay$OverlayPart[]{AbilityOverlay$OverlayPart.BODY};
						Optional var30_1 = var28_1.getShownOverlay(var29_1);
						if (var30_1.isPresent()){
							var18 = ((float)(((AbilityOverlay)(var30_1.get())).getColor().getRed())) / (255F);
							var19 = ((float)(((AbilityOverlay)(var30_1.get())).getColor().getGreen())) / (255F);
							var20 = ((float)(((AbilityOverlay)(var30_1.get())).getColor().getBlue())) / (255F);
							float var31_1 = ((float)(((AbilityOverlay)(var30_1.get())).getColor().getAlpha())) / (255F);
							if (((var15) != (0)) && (var23 instanceof BusoshokuHakiFullBodyHardeningAbility)){
								var31_1 = 0F;
							}
							boolean var32_2 = false;
							boolean var33_4 = false;
							Optional var34_6 = var13.getCurrentMorph();
							if (var34_6.isPresent()){
								var32_2 = ((MorphInfo)(var34_6.get())).hasEqualDepthTest();
								var33_4 = ((MorphInfo)(var34_6.get())).hasCulling();
							}
							if ((((AbilityOverlay)(var30_1.get())).getTexture()) != (null)){
								var16 = var2.getBuffer(ModRenderTypes.getAbilityBody(((AbilityOverlay)(var30_1.get())).getTexture(),var32_2,var33_4));
							}else{
								var16 = (((AbilityOverlay)(var30_1.get())).getRenderType().equals(AbilityOverlay$RenderType.ENERGY)) ? (var2.getBuffer(ModRenderTypes.ENERGY)) : ((VertexConsumer)(var2.getBuffer(ModRenderTypes.TRANSPARENT_COLOR)));
							}
							boolean var35_4 = false;
							Iterator var36_2 = var13.getActiveMorphs().iterator();
							do{
								if (var36_2.hasNext()){
									MorphInfo var37 = ((MorphInfo)(var36_2.next()));
									if (var37.isPartial()){
										EntityModel var38 = ((EntityModel)(ModMorphs$Client.PARTIAL_MORPHS_RENDERERS.get(var37)));
										if ((var38) != (null)){
											var38.renderToBuffer(var1,var16,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(var18,var19,var20,var31_1));
											var35_4 = true;
											break;
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
							if (var35_4){
								return;
							}else{
								this.getParentModel().renderToBuffer(var1,var16,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(var18,var19,var20,var31_1));
								Race var37_1 = ((Race)(((java.util.Optional)(var11.getRace())).orElse((Object)(null))));
								if (((var37_1) != (null)) && (var37_1.hasRenderFeatures())){
									RacialRenderFeature var38_1 = ((RacialRenderFeature)(ModMorphs$Client.RACIAL_RENDERERS.get(var37_1)));
									if ((var37_1.hasSubRaces()) && (var11.getSubRace().isPresent())){
										Race var39 = ((Race)(var11.getSubRace().get()));
										var38_1 = ((RacialRenderFeature)(ModMorphs$Client.RACIAL_RENDERERS.get(var39)));
									}
									if ((var38_1) != (null)){
										var38_1.getModel().renderToBuffer(var1,var16,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(var18,var19,var20,var31_1));
										continue LOOP_1;
									}else{
										continue LOOP_1;
									}
								}else{
									continue LOOP_1;
								}
							}
						}else{
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			return;
		}else{
			return;
		}
	}
}