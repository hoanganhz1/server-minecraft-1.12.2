package xyz.pixelatedw.mineminenomi.handlers.ui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.event.EventHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.OutOfBodyAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SlotDecorationComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class CombatModeHandler {
	// Fields
	public static int abilityBars;
	private static int colorTicks;
	public static boolean isInCombat;
	private static int iconSum;
	private static int iconMode;

	public static void renderOOBVignette(AbstractClientPlayer var0, GuiGraphics var1, int var2, int var3) {
		AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var4) == (null)){
			return;
		}else{
			Predicate<IAbility> var5 = (l0) -> {
			return (l0 instanceof OutOfBodyAbility) && (((OutOfBodyAbility)(l0)).isContinuous());
		};
			Function<IAbility, OutOfBodyAbility> var6 = (l0) -> {
			return ((OutOfBodyAbility)(l0));
		};
			Optional var7 = var4.getEquippedAbilities(var5).stream().map(var6).findFirst();
			if (var7.isPresent()){
				RendererHelper.renderVignette(var1,(Entity)(var0),(float)((((OutOfBodyAbility)(var7.get())).getDistanceFromPivot((Entity)(var0))) / ((double)(((OutOfBodyAbility)(var7.get())).getMaxRange()))),(double)(var2),(double)(var3));
			}
			return;
		}
	}
	public static void renderCombatHotbar(AbstractClientPlayer var0, GuiGraphics var1, Minecraft var2, int var3, int var4, int var5, int var6, float var7) {
		AbilityData var8 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var8) == (null)){
			return;
		}else{
			Predicate<IAbility> var9 = (l0) -> {
			return l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.GAUGE.get()))).isPresent();
		};
			Set var10 = var8.getEquippedAndPassiveAbilities(var9);
			int var11 = 1;
			int var12 = ((var3) / (2)) - (110);
			Iterator var13 = var10.iterator();
			do{
				if (var13.hasNext()){
					IAbility var14 = ((IAbility)(var13.next()));
					if (((Boolean)(var14.getComponent(((AbilityComponentKey)(ModAbilityComponents.PAUSE_TICK.get()))).map((Function)(((Function<PauseTickComponent, Boolean>)(PauseTickComponent::isPaused)))).orElse(Boolean.valueOf(false)))).booleanValue()){
						continue;
					}else{
						Optional var15 = var14.getComponent(((AbilityComponentKey)(ModAbilityComponents.GAUGE.get())));
						if (var15.isPresent()){
							((GaugeComponent)(var15.get())).getRenderer().renderGauge((Player)(var0),var1,(var12) - ((35) * (var11)),var4,var14);
							var11++;
							continue;
						}else{
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			var1.pose().pushPose();
			Gui var14_1 = var2.gui;
			var14_1.rightHeight = (var14_1.rightHeight) + (var6);
			Gui var15_1 = var2.gui;
			var15_1.leftHeight = (var15_1.leftHeight) + (var6);
			int var16 = 0;
			do{
				if ((var16) < (var5)){
					if (((abilityBars) - ((var8.getCombatBarSet()) + (var16))) <= (0)){

					}else{
						var1.pose().translate(0F,0F,-1F);
						int var17 = (1) + ((var8.getCombatBarSet()) + (var16));
						String var18 = "" + var17;
						String var19 = var18;
						RendererHelper.drawStringWithBorder(var2.font,var1,var19,((var3) / (2)) - ((var2.font.width(var19)) + (102)),((var4) - (14)) - ((var16) * (23)),Color.WHITE.getRGB());
						int var20 = 0;
						do{
							if ((var20) < (8)){
								RenderSystem.enableBlend();
								int var21 = (var20) + (((var8.getCombatBarSet()) + (var16)) * (8));
								IAbility var22 = null;
								try{
									var22 = var8.getEquippedAbility(var21);
								}catch(Exception var23){

								}
								if ((var22) != (null)){
									renderAbilitySlot(var1,var2,var0,var3,var4,var22,var16,var20,var7);
								}else{
									var1.blit(ModResources.WIDGETS,(((var3) - (200)) + ((var20) * (50))) / (2),((var4) - (23)) - ((var16) * (23)),0,0,23,23);
								}
								if (ClientConfig.showSlotKeybinds()){
									renderHotbarKeybind(var1,var2.font,var3,var4,var16,var20);
								}
								RenderSystem.disableBlend();
								var20++;
								continue;
							}else{
								break;
							}
						} while (true);
					}
					var16++;
					continue;
				}else{
					break;
				}
			} while (true);
			var1.pose().popPose();
			return;
		}
	}
	private static void renderAbilitySlot(GuiGraphics var0, Minecraft var1, AbstractClientPlayer var2, int var3, int var4, IAbility var5, int var6, int var7, float var8) {
		double var9 = 1D;
		double var10 = 0D;
		Optional var11 = var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get())));
		if (var11.isPresent()){
			SlotDecorationComponent var12 = ((SlotDecorationComponent)(var11.get()));
			var10 = (double)(var12.getCurrentValue());
			var9 = (double)(var12.getMaxValue());
			double var13 = Mth.clamp((23D) - (((var10) / (var9)) * (23D)),0D,1.7976931348623157e+308D);
			int var14 = (((var3) - (200)) + ((var7) * (50))) / (2);
			int var15 = ((var4) - (23)) - ((var6) * (23));
			TexturedRectUI var16 = new TexturedRectUI(ModResources.WIDGETS);
			var12.triggerPreRenderEvents((LivingEntity)(var2),var1,var0.pose(),(MultiBufferSource)(var0.bufferSource()),var16,(float)(var14),(float)(var15),var8);
			var16.reset();
			var16.setColor(var12.getSlotRed(),var12.getSlotGreen(),var12.getSlotBlue(),1F);
			var16.setSize(23F,23F);
			var16.setUV(0F,0F);
			var16.draw(var0.pose(),(float)(var14),(float)(var15));
			var16.reset();
			var16.setSize(23F,(float)((int)(var13)));
			var16.setUV(24F,0F);
			var16.draw(var0.pose(),(float)(var14),(float)(var15));
			RendererHelper.drawIcon(var5.getIcon((LivingEntity)(var2)),var0.pose(),(float)((var14) + (4)),(float)((var15) + (4)),1F,16F,16F,var12.getIconRed(),var12.getIconGreen(),var12.getIconBlue(),var12.getIconAlpha());
			var16.reset();
			var12.triggerPostRenderEvents((LivingEntity)(var2),var1,var0.pose(),(MultiBufferSource)(var0.bufferSource()),var16,(float)(var14),(float)(var15),var8);
		}
		var0.pose().translate(0F,0F,2F);
		if ((ClientConfig.hasTextCooldownVisuals()) && ((var10) > (0D))){
			String var12_1 = null;
			if (ClientConfig.isDisplayInSeconds()){
				String var13_1 = String.format("%.1f",new Object[]{Double.valueOf((var10) / (20D))});
				String var14_1 = var13_1 + " ";
				var12_1 = var14_1;
			}else{
				int var13_2 = (int)(var10);
				String var14_2 = "" + var13_2 + " ";
				var12_1 = var14_2;
			}
			RendererHelper.drawStringWithBorder(var1.font,var0,var12_1,((((var3) - (172)) + ((var7) * (50))) / (2)) - ((var1.font.width(var12_1)) / (2)),((var4) - (14)) - ((var6) * (23)),WyHelper.hexToRGB("#FFFFFF").getRGB());
		}
	}
	private static void renderHotbarKeybind(GuiGraphics var0, Font var1, int var2, int var3, int var4, int var5) {
		int var6 = (var5) + ((var4) * (8));
		KeyMapping var7 = ((KeyMapping)(ModKeybindings.COMBAT_BAR_KEYS.get(var6)));
		StringBuilder var8 = new StringBuilder();
		colorTicks = ((colorTicks) + (1)) % (500);
		if (var7.isUnbound()){
			var8.append(((colorTicks) >= (250)) ? ("§4") : ("§c"));
			var8.append("⚠");
		}else{
			if (InputHandler.isHotbarKeyConflicting(var7)){
				var8.append(((colorTicks) >= (250)) ? ("§4") : ("§c"));
			}
			switch (CombatModeHandler$1.$SwitchMap$net$neoforged$neoforge$client$settings$KeyModifier[var7.getKeyModifier().ordinal()]){
			case 1:
				var8.append("a");
				break;
			case 2:
				var8.append("c");
				break;
			case 3:
				var8.append("s");
				break;
			}
			var8.append(var7.getKey().getDisplayName().getString());
		}
		var0.pose().pushPose();
		var0.pose().translate((float)((((var2) - (195)) + ((var5) * (50))) / (2)),(float)(((var3) - (8)) - ((var4) * (23))),0F);
		var0.pose().scale(0.66F,0.66F,0.66F);
		RendererHelper.drawStringWithBorder(var1,var0,var8.toString(),0,0,-1);
		var0.pose().popPose();
	}
	public static void renderHearts(AbstractClientPlayer var0, GuiGraphics var1, Minecraft var2, int var3, int var4) {
		int var5 = Mth.ceil(var0.getAbsorptionAmount());
		Gui.HeartType var6 = Gui.HeartType.NORMAL;
		if (var0.hasEffect(MobEffects.POISON)){
			var6 = Gui.HeartType.POISIONED;
		}else{
			if (var0.hasEffect(MobEffects.WITHER)){
				var6 = Gui.HeartType.WITHERED;
			}else{
				if (var0.isFullyFrozen()){
					var6 = Gui.HeartType.FROZEN;
				}else{
					if ((var5) > (0)){
						var6 = Gui.HeartType.ABSORBING;
					}else{
						var6 = Gui.HeartType.NORMAL;
					}
				}
			}
		}
		Gui.HeartType var7 = EventHooks.firePlayerHeartTypeEvent((Player)(var0),var6);
		double var8 = var0.getAttribute(Attributes.MAX_HEALTH).getValue();
		double var9 = (double)(var0.getHealth());
		int var10 = (var4) - (var2.gui.leftHeight);
		boolean var11 = var0.level().getLevelData().isHardcore();
		int var12 = Color.RED.getRGB();
		if ((var5) > (0)){
			var12 = Color.YELLOW.getRGB();
		}
		long var13 = Math.round((var9) + ((double)(var5)));
		String var14 = "" + var13;
		RendererHelper.drawStringWithBorder(var2.font,var1,var14,((var3) / (2)) - (27),(var10) + (1),var12);
		int var15 = 10;
		do{
			if ((var15) >= (0)){
				int var16 = (((var3) / (2)) - (91)) + (((var15) % (10)) * (6));
				var1.blitSprite(Gui.HeartType.CONTAINER.getSprite(var11,false,false),var16,var10,9,9);
				var15--;
				continue;
			}else{
				break;
			}
		} while (true);
		double var16_1 = ((100D) - ((((var8) - (var9)) / (var8)) * (100D))) / (10D);
		int var17 = (var0.tickCount) % (25);
		int var18 = 0;
		do{
			if ((var18) < (10)){
				int var19 = (((var3) / (2)) - (91)) + (((var18) % (10)) * (6));
				int var20 = var10;
				int var21 = (int)(Math.floor(var16_1));
				int var22 = 9;
				if ((var18) == (var21)){
					var22 = (int)(((var16_1) * (10D)) % (10D));
				}else{
					if ((var18) > (var21)){
						var22 = 0;
					}
				}
				if (((var21) < (4)) && ((var18) == (var17))){
					var20 = (var20) - (2);
				}
				if ((var18) <= (var21)){
					var1.blitSprite(var7.getSprite(var11,false,false),9,9,0,0,var19,var20,1,var22,9);
				}
				var18++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static void renderCombatIndicator(AbstractClientPlayer var0, GuiGraphics var1, int var2, int var3, int var4) {
		EntityStatsData var5 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var5) == (null)){
			return;
		}else{
			iconSum = 0;
			if (isInCombat){
				iconSum = (iconSum) + (1);
			}
			if (var5.isRogue()){
				iconSum = (iconSum) + (2);
			}
			if ((iconSum) == (3)){
				if ((iconMode) == (0)){
					iconMode = (iconSum) % (2);
				}
				if (((var0.tickCount) % (40)) == (0)){
					if ((iconMode) == (1)){
						iconMode = 2;
					}else{
						if ((iconMode) == (2)){
							iconMode = 1;
						}
					}
				}
			}else{
				iconMode = (iconSum) % (3);
			}
			if ((iconMode) <= (0)){
				return;
			}else{
				int var6 = (var5.isInCombatMode()) ? (var4) : (0);
				switch (iconMode){
				case 1:

				case 2:

				default:
					Object var7 = null;
					if ((null) == (null)){
						return;
					}else{
						var1.pose().pushPose();
						var1.pose().translate((float)(((var2) / (2)) - (128)),(float)(((var3) - (170)) - (var6)),2F);
						var1.pose().translate(128F,128F,0F);
						var1.pose().scale(0.15F,0.15F,1F);
						var1.pose().translate(-128F,-128F,0F);
						RenderSystem.setShaderColor(1F,0F,0F,1F);
						var1.blit((ResourceLocation)(null),0,0,0,0,256,256);
						RenderSystem.setShaderColor(1F,1F,1F,1F);
						var1.pose().popPose();
						return;
					}
				}
			}
		}
	}
	static  {
		abilityBars = 1;
		colorTicks = 1000;
		iconSum = 0;
		iconMode = 0;
	}
}