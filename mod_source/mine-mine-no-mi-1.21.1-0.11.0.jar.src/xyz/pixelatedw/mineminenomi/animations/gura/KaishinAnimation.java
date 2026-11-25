package xyz.pixelatedw.mineminenomi.animations.gura;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class KaishinAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	// Fields
	private static final double SPEED = 20D;

	public KaishinAnimation(AnimationId<KaishinAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		double var8 = Mth.clamp(((double)((80L) - (this.getTime()))) * (20D),-50D,80D);
		var2.leftArm.zRot = (float)(Math.toRadians(var8));
		var2.leftArm.xRot = (float)(Math.toRadians(-10D));
		var2.rightArm.zRot = (float)(Math.toRadians(-(var8)));
		var2.rightArm.xRot = (float)(Math.toRadians(-10D));
	}
}