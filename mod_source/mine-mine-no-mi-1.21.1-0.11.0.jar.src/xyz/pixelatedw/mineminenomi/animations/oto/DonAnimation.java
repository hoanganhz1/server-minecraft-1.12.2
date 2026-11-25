package xyz.pixelatedw.mineminenomi.animations.oto;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class DonAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	// Fields
	private static final float SPEED = 20F;

	public DonAnimation(AnimationId<DonAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = Mth.clamp((-70F) + (((float)(this.getTime())) * (20F)),-70F,-10F);
		var2.leftArm.zRot = (float)(Math.toRadians(80D));
		var2.leftArm.xRot = (float)(Math.toRadians((double)(var8)));
	}
}