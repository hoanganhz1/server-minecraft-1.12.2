package xyz.pixelatedw.mineminenomi.animations.pteranodon;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.morphs.PteranodonFlyModel;

public class PteraOpenMouthAnimation extends Animation<LivingEntity, PteranodonFlyModel<LivingEntity>> {
	public PteraOpenMouthAnimation(AnimationId<PteraOpenMouthAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, PteranodonFlyModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var2.lowerBeak.xRot = (float)(Math.toRadians(40D));
	}
}