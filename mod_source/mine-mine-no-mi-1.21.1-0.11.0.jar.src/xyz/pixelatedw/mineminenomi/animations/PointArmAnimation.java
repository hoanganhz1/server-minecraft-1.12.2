package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class PointArmAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	// Fields
	private HumanoidArm side;

	public PointArmAnimation(AnimationId<PointArmAnimation> var1, HumanoidArm var2) {
		super(var1);
		this.side = HumanoidArm.RIGHT;
		this.side = var2;
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		ModelPart var8 = var2.rightArm;
		int var9 = 1;
		if (this.side.equals(HumanoidArm.LEFT)){
			var8 = var2.leftArm;
			var9 = -1;
		}
		var8.xRot = (((var8.xRot) * (0.5F)) - (3.1415927F)) + (1.8F);
		var8.yRot = (0.2F) * ((float)(var9));
	}
}