package xyz.pixelatedw.mineminenomi.animations.donkrieg;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class MH5Animation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public MH5Animation(AnimationId<MH5Animation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var2.leftArm.xRot = (float)(Math.toRadians(-90D));
		var2.leftArm.yRot = (float)(Math.toRadians(80D));
		ModelPart var8 = var2.leftArm;
		var8.z = (var8.z) - (2F);
	}
}