package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.ThrownSpearEntity;

public class ThrownSpearRenderer extends EntityRenderer<ThrownSpearEntity> {
	protected ThrownSpearRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0.05F;
	}
	public void render(ThrownSpearEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if ((var1.tickCount) < (2)){
			return;
		}else{
			var4.pushPose();
			var4.mulPose(Axis.YP.rotationDegrees((Mth.lerp(var3,var1.yRotO,var1.getYRot())) + (180F)));
			var4.mulPose(Axis.XP.rotationDegrees((Mth.lerp(var3,var1.xRotO,var1.getXRot())) - (130F)));
			ItemStack var7 = var1.getItemUsed();
			if ((var1.getOwner()) != (null)){
				Entity var8 = var1.getOwner();
				if (var8 instanceof LivingEntity){
					LivingEntity var9 = ((LivingEntity)(var8));
					if (((var7) != (null)) && (!(var7.isEmpty()))){
						Minecraft.getInstance().getItemRenderer().renderStatic(var9,var7,ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,false,var4,var5,var1.level(),var6,OverlayTexture.NO_OVERLAY,0);
					}
				}
			}
			var4.popPose();
			return;
		}
	}
	public ResourceLocation getTextureLocation(ThrownSpearEntity var1) {
		return null;
	}
}