package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class CrossedArmsAnimation extends Animation<LivingEntity, AgeableListModel<LivingEntity>> {
	public CrossedArmsAnimation(AnimationId<CrossedArmsAnimation> var1) {
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
				var8.z = -4F;
				var9.z = -4F;
			}
		}
		if ((var8) != (null)){
			var8.xRot = -1.2415929F;
			var8.yRot = -1.2F;
			var8.zRot = -0.8F;
		}
		if ((var9) != (null)){
			var9.xRot = -1.2415929F;
			var9.yRot = 1.2F;
			var9.zRot = 0.8F;
		}
	}
}