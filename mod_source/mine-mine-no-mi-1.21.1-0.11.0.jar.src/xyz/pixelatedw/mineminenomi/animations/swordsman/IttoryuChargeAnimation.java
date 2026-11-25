package xyz.pixelatedw.mineminenomi.animations.swordsman;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class IttoryuChargeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public IttoryuChargeAnimation(AnimationId<IttoryuChargeAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = ((float)(Math.min(this.getAnimationInitialDuration(),10))) * (0.8F);
		float var9 = EasingFunctionHelper.easeOutCubic(Float.valueOf((Math.min((float)(this.getTime()),var8)) / (var8)));
		var2.head.y = (3F) * (var9);
		var2.head.z = (-4.5F) * (var9);
		var2.body.z = (-5F) * (var9);
		var2.body.y = (3F) * (var9);
		var2.body.xRot = ((float)(Math.toRadians(20D))) * (var9);
		var2.rightArm.zRot = ((float)(Math.toRadians(-5D))) * (var9);
		var2.rightArm.xRot = ((float)(Math.toRadians(-10D))) * (var9);
		var2.rightArm.z = (-4F) * (var9);
		var2.rightArm.y = (3F) * (var9);
		var2.leftArm.z = -5F;
		ModelPart var10 = var2.leftArm;
		var10.y = (var10.y) + (2F);
		ModelPart var11 = var2.leftArm;
		var11.x = (var11.x) - (2F);
		var2.leftArm.xRot = ((float)(Math.toRadians(-40D))) * (var9);
		var2.leftArm.zRot = ((float)(Math.toRadians(20D))) * (var9);
		var2.leftArm.yRot = ((float)(Math.toRadians(40D))) * (var9);
		var2.rightLeg.z = (-2F) * (var9);
		var2.rightLeg.xRot = ((float)(Math.toRadians(-5D))) * (var9);
		var2.leftLeg.z = (-0.5F) * (var9);
		var2.leftLeg.xRot = ((float)(Math.toRadians(15D))) * (var9);
		var2.leftLeg.yRot = ((float)(Math.toRadians(-10D))) * (var9);
		var2.leftLeg.zRot = ((float)(Math.toRadians(-5D))) * (var9);
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		float var8 = ((float)(Math.min(this.getAnimationInitialDuration(),10))) * (0.8F);
		float var9 = EasingFunctionHelper.easeOutCubic(Float.valueOf((Math.min((float)(this.getTime()),var8)) / (var8)));
		var5.translate(0D,-0.3D,0.1D);
		var5.mulPose(Axis.ZP.rotationDegrees((-180F) * (var9)));
		var5.mulPose(Axis.XP.rotationDegrees((30F) * (var9)));
	}
}