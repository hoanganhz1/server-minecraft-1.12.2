package xyz.pixelatedw.mineminenomi.handlers.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ClientBossExtraEvent;
import xyz.pixelatedw.mineminenomi.data.entity.CombatData;

public class BossUIHandler {
	public static int renderBossHealth(AbstractClientPlayer var0, GuiGraphics var1, LerpingBossEvent var2, int var3, int var4) {
		CombatData var5 = ((CombatData)(((java.util.Optional)(CombatData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var5) == (null)){
			return -1;
		}else{
			int var7 = -1;
			var1.pose().pushPose();
			RenderSystem.enableBlend();
			Iterator var8 = var5.getExtraBossInfo().entrySet().iterator();
			LOOP_1:
			do{
				if (var8.hasNext()){
					Map.Entry var9 = ((Map.Entry)(var8.next()));
					if (var2.getId().equals(var9.getKey())){
						int var10 = ((ClientBossExtraEvent)(var9.getValue())).getTotalBars();
						int var11 = ((ClientBossExtraEvent)(var9.getValue())).getActiveBars();
						int var12 = var10;
						do{
							if ((var12) > (0)){
								int var13 = ((var3) + (183)) - (((var12) % (19)) * (10));
								int var14 = (var4) + (6);
								if ((var12) >= (19)){
									var14 = (var14) + (10);
								}
								var1.blitSprite(Gui.HeartType.CONTAINER.getSprite(false,false,false),var13,var14,9,9);
								if ((var12) > (var11)){

								}else{
									var1.blitSprite(Gui.HeartType.NORMAL.getSprite(false,false,false),var13,var14,9,9);
								}
								var12--;
								continue;
							}else{
								break;
							}
						} while (true);
						if (((var10) / (19)) > (0)){
							var7 = (18) * ((var10) / (19));
							continue LOOP_1;
						}else{
							continue LOOP_1;
						}
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			RenderSystem.disableBlend();
			var1.pose().popPose();
			return var7;
		}
	}
}