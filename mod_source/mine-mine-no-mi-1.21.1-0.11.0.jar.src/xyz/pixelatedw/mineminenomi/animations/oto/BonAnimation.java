package xyz.pixelatedw.mineminenomi.animations.oto;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BonAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	// Fields
	private static final float SPEED = 15F;

	public BonAnimation(AnimationId<BonAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var2.leftArm.zRot = (float)(Math.toRadians(30D));
		var2.leftArm.xRot = (float)(Math.toRadians(-50D));
		var2.rightArm.zRot = (float)(Math.toRadians(-60D));
		float var8 = Mth.clamp((0F) + (((float)(this.getTime())) * (15F)),0F,30F);
		var2.rightArm.xRot = (float)(Math.toRadians((double)((-60F) + (var8))));
	}
}