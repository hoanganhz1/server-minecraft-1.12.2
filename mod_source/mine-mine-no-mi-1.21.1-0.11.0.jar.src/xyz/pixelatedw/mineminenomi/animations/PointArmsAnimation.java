package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class PointArmsAnimation extends Animation<LivingEntity, AgeableListModel<LivingEntity>> {
	public PointArmsAnimation(AnimationId<PointArmsAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, AgeableListModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		ModelPart var8 = null;
		ModelPart var9 = null;
		if (var2 instanceof HumanoidModel){
			HumanoidModel var10 = ((HumanoidModel)(var2));
			var8 = var10.rightArm;
			var9 = var10.leftArm;
		}else{
			if (var2 instanceof DugongModel){
				DugongModel var10_1 = ((DugongModel)(var2));
				var8 = var10_1.rightArm;
				var9 = var10_1.leftArm;
			}
		}
		if ((var8) != (null)){
			var8.xRot = (((var8.xRot) * (0.5F)) - (3.1415927F)) + (1.8F);
			var8.yRot = -0.2F;
		}
		if ((var9) != (null)){
			var9.xRot = (((var9.xRot) * (0.5F)) - (3.1415927F)) + (1.8F);
			var9.yRot = 0.2F;
		}
	}
}