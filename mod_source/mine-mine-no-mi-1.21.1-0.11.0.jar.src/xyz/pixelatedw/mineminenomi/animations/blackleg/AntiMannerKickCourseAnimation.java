package xyz.pixelatedw.mineminenomi.animations.blackleg;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class AntiMannerKickCourseAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public AntiMannerKickCourseAnimation(AnimationId<AntiMannerKickCourseAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = ((float)(this.getAnimationInitialDuration())) * (0.7F);
		float var9 = (Math.min((float)(this.getTime()),var8)) / (var8);
		float var10 = EasingFunctionHelper.easeInSine(Float.valueOf(var9));
		float var11 = EasingFunctionHelper.easeInCubic(Float.valueOf(var9));
		var2.head.y = 1F;
		var2.head.z = 3.5F;
		var2.body.x = 1F;
		var2.body.y = 1F;
		var2.body.z = 2.5F;
		var2.body.xRot = ((float)(Math.toRadians(-5D))) * (var10);
		var2.body.yRot = ((float)(Math.toRadians(25D))) * (var10);
		var2.rightArm.x = -4.5F;
		var2.rightArm.z = 4F;
		var2.rightArm.yRot = ((float)(Math.toRadians(25D))) * (var10);
		var2.rightArm.zRot = ((float)(Math.toRadians(25D))) * (var10);
		var2.rightArm.xRot = 0F;
		var2.leftArm.z = -2F;
		var2.leftArm.xRot = ((float)(Math.toRadians(-190D))) * (var10);
		var2.leftArm.yRot = ((float)(Math.toRadians(-20D))) * (var10);
		var2.leftArm.zRot = ((float)(Math.toRadians(-20D))) * (var10);
		var2.rightLeg.z = -3F;
		var2.rightLeg.y = 8F;
		var2.rightLeg.xRot = ((float)(Math.toRadians(180D))) * (-(var11));
		var2.rightLeg.yRot = 0F;
		var2.rightLeg.zRot = 0F;
	}
}