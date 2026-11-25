package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.GenocideRaidEffectEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class GenocideRaidRenderer<T extends GenocideRaidEffectEntity> extends EntityRenderer<T> {
	protected GenocideRaidRenderer(EntityRendererProvider.Context var1) {
		super(var1);
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		var4.pushPose();
		var4.mulPose(Axis.XN.rotationDegrees(180F));
		var4.translate(0.75D,0.35D,0.75D);
		var4.scale(1F,1F,1F);
		if (((var1.getTarget()) != (null)) && (var1.getTarget().hasEffect((Holder)(ModEffects.GENOCIDE_RAID)))){
			int var11 = 0;
			LOOP_1:
			do{
				if ((var11) < (5)){
					float var12 = ((float)(var11)) * (72F);
					float var13 = 0F;
					do{
						if ((var13) < (5F)){
							float var14 = (var13) * (72F);
							var4.pushPose();
							var4.translate(-0.75F,-0.75F,-0.75F);
							var4.mulPose(Axis.YN.rotationDegrees((var12) + (((float)(var1.tickCount)) * (10F))));
							var4.mulPose(Axis.ZN.rotationDegrees((var14) + (((float)(var1.tickCount)) * (10F))));
							var4.translate(0.75F,0.75F,0.75F);
							int var15 = (int)((((float)(var11)) + (var13)) % ((float)(var1.getItemsUsed().size())));
							ItemStack var16 = ((ItemStack)(var1.getItemsUsed().get(var15)));
							Minecraft.getInstance().getItemRenderer().renderStatic(var1.getTarget(),var16,ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,false,var4,var5,var1.level(),var6,OverlayTexture.NO_OVERLAY,0);
							var4.popPose();
							var13 = (var13) + (1F);
							continue;
						}else{
							break;
						}
					} while (true);
					var11++;
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
			var4.popPose();
			return;
		}else{
			var4.popPose();
			return;
		}
	}
	public ResourceLocation getTextureLocation(T var1) {
		return null;
	}
}