package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ThrowChakramAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public ThrowChakramAnimation(AnimationId<ThrowChakramAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		long var8 = this.getTime();
		var8 = (WyHelper.clamp(var8,0L,40L)) * (4L);
		double var9 = (double)((-130L) - (var8));
		var9 = Mth.clamp(var9,-170D,-130D);
		var2.rightArm.z = -3F;
		var2.rightArm.y = 3.5F;
		var2.rightArm.yRot = 0F;
		var2.rightArm.zRot = (float)(Math.toRadians(95D));
		var2.rightArm.xRot = (float)(Math.toRadians(var9));
		var2.leftArm.xRot = 0F;
		var2.leftArm.yRot = 0F;
		var2.leftArm.zRot = 0F;
	}
}