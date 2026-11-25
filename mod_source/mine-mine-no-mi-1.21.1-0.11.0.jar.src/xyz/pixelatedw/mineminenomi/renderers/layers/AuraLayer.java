package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SlotDecorationComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypeBuffers;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.renderers.buffers.HakiAuraBuffer;

public class AuraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final HashMap<Integer, ArrayList<AuraLayer$AbilityDisplayData>> ABILITIES_TO_SHOW = new HashMap();
	private static final long PHASE_IN_DURATION = 500L;
	private static final long FULL_VISIBLE_DURATION = 3000L;
	private static final long PHASE_OUT_DURATION = 500L;

	public AuraLayer(RenderLayerParent<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		Minecraft var11 = Minecraft.getInstance();
		LocalPlayer var12 = var11.player;
		if ((var4) == (var12)){
			return;
		}else{
			Function<AbilityData, KenbunshokuHakiAuraAbility> var13 = (l0) -> {
			return ((KenbunshokuHakiAuraAbility)(l0.getEquippedAbility(((AbilityCore)(KenbunshokuHakiAuraAbility.INSTANCE.get())))));
		};
			KenbunshokuHakiAuraAbility var14 = ((KenbunshokuHakiAuraAbility)(((java.util.Optional)(AbilityData.get((LivingEntity)(var12)))).map((Function)(var13)).orElse(null)));
			if (((var14) != (null)) && (var14.isContinuous())){
				double var15 = var14.getViewDistance((Player)(var12));
				if (((double)(var4.distanceTo((Entity)(var12)))) > (var15)){
					return;
				}else{
					var1.pushPose();
					int var16 = 5592575;
					if (var4 instanceof Animal){
						var16 = 5635925;
					}else{
						if (var4 instanceof Monster){
							var16 = 16711680;
						}else{
							if (var4 instanceof Player){
								var16 = 65535;
							}
						}
					}
					int var17 = (-16777216) | (var16);
					int var18 = ((var17) >> (16)) & (255);
					int var19 = ((var17) >> (8)) & (255);
					int var20 = ((var17) >> (0)) & (255);
					HakiAuraBuffer var21 = ModRenderTypeBuffers.getInstance().getHakiAuraBuffer();
					var21.setColor(var18,var19,var20,127);
					VertexConsumer var22 = var21.getBuffer(ModRenderTypes.getAuraRenderType());
					this.getParentModel().renderToBuffer(var1,var22,var3,OverlayTexture.NO_OVERLAY);
					var21.endBatch();
					var1.popPose();
					if ((((Float)(HakiData.get((LivingEntity)(var12)).map((Function)(((Function<HakiData, Float>)(HakiData::getKenbunshokuHakiExp)))).orElse(Float.valueOf(0F)))).floatValue()) < (50F)){
						return;
					}else{
						ArrayList var23 = ((ArrayList)(ABILITIES_TO_SHOW.get(Integer.valueOf(var4.getId()))));
						if ((var23) == (null)){
							return;
						}else{
							float var24 = 0F;
							int var25 = 0;
							do{
								if ((var25) < (var23.size())){
									AuraLayer$AbilityDisplayData var26 = ((AuraLayer$AbilityDisplayData)(var23.get(var25)));
									IAbility var27 = var26.ability;
									long var28 = (System.currentTimeMillis()) - (var26.startTime);
									float var29 = 0F;
									Optional var30 = var27.getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get())));
									if (var30.isPresent()){
										SlotDecorationComponent var31 = ((SlotDecorationComponent)(var30.get()));
										TexturedRectUI var32 = new TexturedRectUI(ModResources.WIDGETS);
										var31.triggerPreRenderEvents((LivingEntity)(var12),var11,var1,var2,var32,1.25F,((-(var4.getEyeHeight())) + (1F)) - (var24),var7);
										if ((var28) <= (500L)){
											var29 = ((var31.getIconAlpha()) / (500F)) * ((float)(var28));
										}else{
											if ((var28) <= (3500L)){
												var29 = var31.getIconAlpha();
											}else{
												if ((var28) <= (4000L)){
													var29 = (var31.getIconAlpha()) - (((float)((var28) - (3500L))) / (500F));
												}
											}
										}
										var1.pushPose();
										float var33 = var31.getCurrentValue();
										float var34 = Mth.clamp((23F) - (((var33) / (var31.getMaxValue())) * (23F)),0F,3.4028235e+38F);
										float var35 = ((var29) / (var31.getIconAlpha())) * (1F);
										float var36 = ((-(var4.getEyeHeight())) + (1F)) - (var24);
										var32.reset();
										var32.setColor(var31.getSlotRed(),var31.getSlotGreen(),var31.getSlotBlue(),var35);
										var32.setSize(23F,23F);
										var32.setScale(0.0625F,0.0625F);
										var32.setUV(0F,0F);
										var32.draw(var1,1.25F,var36);
										var32.reset();
										var32.setColor(1F,1F,1F,var35);
										var32.setUV(0F,0F);
										var32.setSize(23F,var34);
										var32.setScale(0.0625F,0.0625F);
										var32.draw(var1,1.25F,var36);
										var1.mulPose(Axis.YP.rotationDegrees(180F));
										var32.reset();
										var32.setColor(var31.getSlotRed(),var31.getSlotGreen(),var31.getSlotBlue(),var35);
										var32.setSize(23F,23F);
										var32.setScale(0.0625F,0.0625F);
										var32.setUV(0F,0F);
										var32.draw(var1,-2.25F,var36);
										var32.reset();
										var32.setColor(1F,1F,1F,var35);
										var32.setUV(0F,0F);
										var32.setSize(23F,var34);
										var32.setScale(0.0625F,0.0625F);
										var32.draw(var1,-2.25F,var36);
										var1.popPose();
										var1.pushPose();
										RendererHelper.drawIcon(var27.getIcon(var4),var1,1.5F,(((-(var4.getEyeHeight())) + (1F)) - (var24)) + (0.25F),0F,1F,1F,var31.getIconRed(),var31.getIconGreen(),var31.getIconBlue(),var29);
										var1.mulPose(Axis.YP.rotationDegrees(180F));
										RendererHelper.drawIcon(var27.getIcon(var4),var1,-2F,(((-(var4.getEyeHeight())) + (1F)) - (var24)) + (0.25F),-0F,1F,1F,var31.getIconRed(),var31.getIconGreen(),var31.getIconBlue(),var29,false);
										var1.popPose();
										var24 = (var24) + (1.5F);
									}
									if ((var28) >= (4000L)){
										var23.remove(var26);
									}
									var25++;
									continue;
								}else{
									break;
								}
							} while (true);
							return;
						}
					}
				}
			}else{
				return;
			}
		}
	}
	public static void addAbilityIcon(LivingEntity var0, IAbility var1) {
		if (!(var0.level().isClientSide)){
			return;
		}else{
			ABILITIES_TO_SHOW.putIfAbsent(Integer.valueOf(var0.getId()),new ArrayList());
			ArrayList var2 = ((ArrayList)(ABILITIES_TO_SHOW.get(Integer.valueOf(var0.getId()))));
			boolean var3 = false;
			Iterator var4 = var2.iterator();
			do{
				if (var4.hasNext()){
					if (((AuraLayer$AbilityDisplayData)(var4.next())).ability.equals(var1)){
						var3 = true;
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if (!(var3)){
				var2.add(0,new AuraLayer$AbilityDisplayData(var1,System.currentTimeMillis()));
			}
			return;
		}
	}
}