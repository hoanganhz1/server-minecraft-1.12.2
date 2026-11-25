package xyz.pixelatedw.mineminenomi.animations.supa;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class SparklingDaisyChargeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	// Fields
	private static final float SPEED = 0.07F;

	public SparklingDaisyChargeAnimation(AnimationId<SparklingDaisyChargeAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = ((float)(this.getTime())) * (0.07F);
		float var9 = 0.7F;
		if ((var8) < (var9)){
			var2.rightArm.xRot = (float)(Math.toRadians(300D));
			var2.rightArm.yRot = (float)(Math.toRadians(30D));
			var2.rightArm.zRot = (float)(Math.toRadians(-30D));
			var2.leftArm.xRot = (float)(Math.toRadians(300D));
			var2.leftArm.yRot = (float)(Math.toRadians(-30D));
			var2.leftArm.zRot = (float)(Math.toRadians(30D));
		}else{
			double var10 = (double)((var8) - (var9));
			var2.rightArm.xRot = (float)(Math.toRadians(300D));
			var2.rightArm.yRot = (float)(Mth.clamp((var10) + ((double)((float)(Math.toRadians(30D)))),0D,2.5D));
			var2.rightArm.zRot = (float)(Math.toRadians(-30D));
			var2.leftArm.xRot = (float)(Math.toRadians(300D));
			var2.leftArm.yRot = (float)(Mth.clamp((-(var10)) + ((double)((float)(Math.toRadians(-30D)))),-2.5D,0D));
			var2.leftArm.zRot = (float)(Math.toRadians(30D));
		}
	}
}