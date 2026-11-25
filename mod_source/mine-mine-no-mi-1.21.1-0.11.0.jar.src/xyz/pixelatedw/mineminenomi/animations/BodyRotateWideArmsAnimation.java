package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BodyRotateWideArmsAnimation extends BodyRotateAnimation {
	public BodyRotateWideArmsAnimation(AnimationId<BodyRotateWideArmsAnimation> var1, float var2) {
		super((AnimationId)(var1),var2);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, AgeableListModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if (var2 instanceof HumanoidModel){
			HumanoidModel var8 = ((HumanoidModel)(var2));
			var8.rightArm.xRot = (float)(Math.toRadians(-90D));
			var8.rightArm.zRot = (float)(Math.toRadians(-90D));
			var8.leftArm.xRot = (float)(Math.toRadians(90D));
			var8.leftArm.zRot = (float)(Math.toRadians(-90D));
		}
	}
}