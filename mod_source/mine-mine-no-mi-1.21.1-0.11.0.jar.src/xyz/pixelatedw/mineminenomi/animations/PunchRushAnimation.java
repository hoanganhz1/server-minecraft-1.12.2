package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class PunchRushAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public PunchRushAnimation(AnimationId<PunchRushAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = -(Mth.cos((var5) * (2.2F)));
		var2.rightArm.z = (5F) * ((0.5F) - (var8));
		var2.rightArm.xRot = (float)(Math.toRadians(-90D));
		var2.rightArm.yRot = 0F;
		float var9 = Mth.cos((var5) * (2.2F));
		var2.leftArm.z = (5F) * ((0.5F) - (var9));
		var2.leftArm.xRot = (float)(Math.toRadians(-90D));
		var2.leftArm.yRot = 0F;
	}
}