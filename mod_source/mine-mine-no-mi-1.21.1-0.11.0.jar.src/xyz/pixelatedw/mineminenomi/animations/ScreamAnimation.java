package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class ScreamAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public ScreamAnimation(AnimationId<ScreamAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var1.yBodyRot = var1.yHeadRot;
		float var8 = ((float)(this.getAnimationInitialDuration())) * (0.3F);
		float var9 = EasingFunctionHelper.easeInBack(Float.valueOf((Math.min((float)(this.getTime()),var8)) / (var8)));
		var2.head.z = (-0.5F) * (var9);
		var2.head.y = (1F) * (var9);
		var2.head.xRot = 0F;
		var2.head.yRot = 0F;
		var2.head.zRot = 0F;
		var2.body.y = (1F) * (var9);
		var2.rightArm.z = 1F;
		var2.rightArm.y = 3F;
		var2.rightArm.xRot = ((float)(Math.toRadians(20D))) * (var9);
		var2.rightArm.zRot = ((float)(Math.toRadians(45D))) * (var9);
		var2.leftArm.z = 1F;
		var2.leftArm.y = 3F;
		var2.leftArm.xRot = ((float)(Math.toRadians(20D))) * (var9);
		var2.leftArm.zRot = ((float)(Math.toRadians(-45D))) * (var9);
		var2.rightLeg.xRot = ((float)(Math.toRadians(5D))) * (var9);
		var2.rightLeg.yRot = ((float)(Math.toRadians(10D))) * (var9);
		var2.rightLeg.zRot = ((float)(Math.toRadians(10D))) * (var9);
		var2.rightLeg.x = (-2F) - ((0.5F) * (var9));
		var2.rightLeg.z = 0F;
		var2.rightLeg.y = 12F;
		var2.leftLeg.xRot = ((float)(Math.toRadians(5D))) * (var9);
		var2.leftLeg.yRot = ((float)(Math.toRadians(-10D))) * (var9);
		var2.leftLeg.zRot = ((float)(Math.toRadians(-10D))) * (var9);
		var2.leftLeg.x = (2F) + ((0.5F) * (var9));
		var2.leftLeg.z = 0F;
		var2.leftLeg.y = 12F;
	}
}