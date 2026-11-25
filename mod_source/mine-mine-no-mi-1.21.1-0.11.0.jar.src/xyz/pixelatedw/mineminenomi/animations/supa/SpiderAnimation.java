package xyz.pixelatedw.mineminenomi.animations.supa;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class SpiderAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public SpiderAnimation(AnimationId<SpiderAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		ModelPart var8 = var2.rightArm;
		var8.x = (var8.x) - (5F);
		ModelPart var9 = var2.rightArm;
		var9.z = (var9.z) - (4F);
		ModelPart var10 = var2.rightArm;
		var10.y = (var10.y) + (5F);
		var2.rightArm.xRot = 0F;
		var2.rightArm.yRot = (float)(Math.toRadians(180D));
		var2.rightArm.zRot = (float)(Math.toRadians(-90D));
		ModelPart var11 = var2.leftArm;
		var11.x = (var11.x) + (5F);
		ModelPart var12 = var2.leftArm;
		var12.z = (var12.z) - (4F);
		ModelPart var13 = var2.leftArm;
		var13.y = (var13.y) + (5F);
		var2.leftArm.xRot = 0F;
		var2.leftArm.yRot = (float)(Math.toRadians(180D));
		var2.leftArm.zRot = (float)(Math.toRadians(90D));
		var2.head.y = 3F;
		var2.body.y = 3F;
		var2.rightLeg.x = -6F;
		var2.rightLeg.z = 0F;
		var2.rightLeg.y = 12F;
		var2.leftLeg.x = 6F;
		var2.leftLeg.z = 0F;
		var2.leftLeg.y = 12F;
	}
}