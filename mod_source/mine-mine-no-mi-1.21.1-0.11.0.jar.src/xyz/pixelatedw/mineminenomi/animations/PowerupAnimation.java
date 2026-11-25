package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class PowerupAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public PowerupAnimation(AnimationId<? extends PowerupAnimation> var1) {
		super((AnimationId)(var1));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var2.rightArm.xRot = (float)(Math.toRadians(300D));
		var2.rightArm.yRot = (float)(Math.toRadians(30D));
		var2.rightArm.zRot = (float)(Math.toRadians(-30D));
		var2.leftArm.xRot = (float)(Math.toRadians(300D));
		var2.leftArm.yRot = (float)(Math.toRadians(-30D));
		var2.leftArm.zRot = (float)(Math.toRadians(30D));
		var2.body.xRot = 0.5F;
		var2.rightLeg.z = 4F;
		var2.leftLeg.z = 4F;
		var2.rightLeg.y = 12.2F;
		var2.leftLeg.y = 12.2F;
		ModelPart var8 = var2.head;
		var8.y = (var8.y) + (4.2F);
		ModelPart var9 = var2.hat;
		var9.y = (var9.y) + (4.2F);
		var2.body.y = 3.2F;
		var2.leftArm.y = 5.2F;
		ModelPart var10 = var2.leftArm;
		var10.z = (var10.z) + (3F);
		var2.rightArm.y = 5.2F;
		ModelPart var11 = var2.rightArm;
		var11.z = (var11.z) + (3F);
	}
}