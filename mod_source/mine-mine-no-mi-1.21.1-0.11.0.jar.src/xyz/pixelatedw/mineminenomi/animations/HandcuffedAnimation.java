package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class HandcuffedAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public HandcuffedAnimation(AnimationId<HandcuffedAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var2.rightArm.xRot = (float)(Math.toRadians(-40D));
		var2.leftArm.xRot = (float)(Math.toRadians(-40D));
	}
}