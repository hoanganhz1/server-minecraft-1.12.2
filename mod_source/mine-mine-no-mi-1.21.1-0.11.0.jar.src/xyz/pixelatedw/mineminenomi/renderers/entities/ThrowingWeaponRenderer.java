package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.projectiles.ThrowingWeaponEntity;

public class ThrowingWeaponRenderer<T extends ThrowingWeaponEntity, M extends EntityModel<T>> extends EntityRenderer<T> {
	public ThrowingWeaponRenderer(EntityRendererProvider.Context var1) {
		super(var1);
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if ((var1.tickCount) < (2)){
			return;
		}else{
			var4.pushPose();
			var4.translate(0F,0.5F,0F);
			var4.mulPose(Axis.YP.rotationDegrees((Mth.lerp(var3,var1.yRotO,var1.getYRot())) + (180F)));
			var4.mulPose(Axis.XN.rotationDegrees(Mth.lerp(var3,var1.xRotO,var1.getXRot())));
			var4.mulPose(Axis.XN.rotationDegrees(var1.getRotation()));
			ItemStack var7 = var1.getItem();
			LivingEntity var8 = var1.getOwner();
			if (((var7) != (null)) && (!(var7.isEmpty()))){
				Minecraft.getInstance().getItemRenderer().renderStatic(var8,var7,ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,false,var4,var5,var1.level(),var6,OverlayTexture.NO_OVERLAY,(var1.getId()) + (ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal()));
			}
			var4.popPose();
			return;
		}
	}
	public ResourceLocation getTextureLocation(T var1) {
		return null;
	}
}