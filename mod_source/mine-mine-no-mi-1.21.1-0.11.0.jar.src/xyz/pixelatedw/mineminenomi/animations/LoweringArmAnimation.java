package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class LoweringArmAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public LoweringArmAnimation(AnimationId<LoweringArmAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = (float)(this.getTime());
		float var9 = 0F;
		float var10 = 15F;
		if ((var8) > (var10)){
			var9 = Mth.clamp(((0.2F) * ((var8) - (var10))) * (3.1415927F),0F,3F);
		}
		var2.rightArm.xRot = ((float)(Math.toRadians(180D))) + (var9);
	}
}