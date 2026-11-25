package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ChargeNetThrowAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public ChargeNetThrowAnimation(AnimationId<ChargeNetThrowAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		int var8 = (var1.getItemInHand(InteractionHand.MAIN_HAND).getUseDuration(var1)) - (var1.getUseItemRemainingTicks());
		var8 = Mth.clamp(var8,0,20);
		double var9 = (20D) + (((double)(var8)) * (0.2D));
		double var10 = (double)((-10) + (var8));
		double var11 = (5D) + (((double)(var8)) * (0.3D));
		double var12 = (0D) + (((double)(var8)) * (0.5D));
		double var13 = (25D) + (((double)(var8)) * (0.5D));
		var2.body.yRot = (float)(Math.toRadians(var9));
		var2.rightArm.x = -4F;
		var2.rightArm.zRot = (float)(Math.toRadians(var11));
		var2.rightArm.xRot = (float)(Math.toRadians(var10));
		var2.leftArm.x = 1F;
		var2.leftArm.y = 1.75F;
		var2.leftArm.z = -2.5F;
		var2.leftArm.zRot = (float)(Math.toRadians(var13));
		var2.leftArm.xRot = (float)(Math.toRadians(var12));
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		var5.translate(-0.1D,-0.3D,-0.05D);
		var5.mulPose(Axis.YP.rotationDegrees(-5F));
	}
}