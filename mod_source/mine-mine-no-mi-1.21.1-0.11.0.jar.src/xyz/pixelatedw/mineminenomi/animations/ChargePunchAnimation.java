package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ChargePunchAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public ChargePunchAnimation(AnimationId<ChargePunchAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = ((float)(this.getTime())) * (0.1F);
		ModelPart var9 = var2.rightArm;
		var9.z = (var9.z) + (4F);
		var2.rightArm.yRot = (float)(Math.toRadians(20D));
		var2.rightArm.xRot = ((float)(Math.toRadians(-90D))) + ((Mth.sin((float)((((double)(var8)) * (3.141592653589793D)) * (1.8D)))) / (7F));
		var2.rightArm.zRot = (Mth.cos((float)((((double)(var8)) * (3.141592653589793D)) * (1.8D)))) / (7F);
		var2.leftArm.xRot = (float)(Math.toRadians(-80D));
		var2.leftArm.yRot = (float)(Math.toRadians(15D));
	}
}