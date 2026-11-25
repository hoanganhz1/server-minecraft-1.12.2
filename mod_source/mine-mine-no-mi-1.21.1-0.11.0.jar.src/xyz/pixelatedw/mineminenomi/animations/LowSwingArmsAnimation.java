package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class LowSwingArmsAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public LowSwingArmsAnimation(AnimationId<LowSwingArmsAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = 0.3F;
		float var9 = 0.1F;
		var2.rightArm.xRot = (float)(Math.toRadians(-10D));
		var2.rightArm.yRot = (float)((Math.cos((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * ((double)(var8)));
		var2.rightArm.zRot = (float)((Math.sin((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * ((double)(var8)));
		var2.rightArm.z = -2F;
		var2.rightArm.y = 2F;
		ModelPart var10 = var2.rightArm;
		var10.yRot = (float)(((double)(var10.yRot)) + (Math.toRadians(90D)));
		var2.leftArm.xRot = (float)(Math.toRadians(-10D));
		var2.leftArm.yRot = (float)((Math.cos((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * ((double)(var8)));
		var2.leftArm.zRot = (float)((Math.sin((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * ((double)(var8)));
		var2.leftArm.z = -2F;
		var2.leftArm.y = 2F;
		ModelPart var11 = var2.leftArm;
		var11.yRot = (float)(((double)(var11.yRot)) - (Math.toRadians(-90D)));
	}
}