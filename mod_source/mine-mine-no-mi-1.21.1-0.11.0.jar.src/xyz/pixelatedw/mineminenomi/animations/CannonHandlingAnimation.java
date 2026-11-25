package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class CannonHandlingAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public CannonHandlingAnimation(AnimationId<CannonHandlingAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var2.body.xRot = 0.5F;
		ModelPart var8 = var2.rightArm;
		var8.xRot = (var8.xRot) - (0.8F);
		ModelPart var9 = var2.leftArm;
		var9.xRot = (var9.xRot) - (0.8F);
		var2.rightLeg.z = 4F;
		var2.leftLeg.z = 4F;
		var2.rightLeg.y = 12.2F;
		var2.leftLeg.y = 12.2F;
		var2.head.y = 4.2F;
		var2.body.y = 3.2F;
		var2.leftArm.y = 5.2F;
		var2.rightArm.y = 5.2F;
	}
}