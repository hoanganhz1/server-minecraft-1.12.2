package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ScratchingAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public ScratchingAnimation(AnimationId<ScratchingAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = 0.9F;
		float var9 = 0.5F;
		var2.rightArm.xRot = (float)(Math.toRadians(-90D));
		ModelPart var10 = var2.rightArm;
		var10.xRot = (var10.xRot) + ((float)((Math.sin((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * ((double)(var8))));
		var2.rightArm.zRot = (-0.3F) - ((float)((Math.sin((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * (-0.3D)));
		ModelPart var11 = var2.rightArm;
		var11.yRot = (float)(((double)(var11.yRot)) + ((Math.sin((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * (0.2D)));
		var2.leftArm.xRot = (float)(Math.toRadians(-90D));
		ModelPart var12 = var2.leftArm;
		var12.xRot = (var12.xRot) + ((float)((Math.cos((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * ((double)(var8))));
		var2.leftArm.zRot = (0.3F) - ((float)((Math.cos((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * (0.3D)));
		ModelPart var13 = var2.leftArm;
		var13.yRot = (float)(((double)(var13.yRot)) - ((Math.cos((((double)(var5)) * (3.141592653589793D)) * ((double)(var9)))) * (0.2D)));
	}
}