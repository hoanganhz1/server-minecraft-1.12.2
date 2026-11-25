package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.SeaCowModel;

public class TackleAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
	// Fields
	private HumanoidArm side;

	public TackleAnimation(AnimationId<TackleAnimation> var1, HumanoidArm var2) {
		super(var1);
		this.side = HumanoidArm.RIGHT;
		this.side = var2;
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, EntityModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if (var2 instanceof HumanoidModel){
			HumanoidModel var8 = ((HumanoidModel)(var2));
			ModelPart var9 = var8.rightArm;
			ModelPart var10 = var8.leftArm;
			int var11 = 1;
			if (this.side.equals(HumanoidArm.LEFT)){
				var9 = var8.leftArm;
				var10 = var8.rightArm;
				var11 = -1;
			}
			var8.body.yRot = -0.5F;
			var9.z = -1.2F;
			var9.x = -3F;
			var9.xRot = -1.9415928F;
			var9.yRot = (-1.5F) * ((float)(var11));
			var9.zRot = (0.8F) * ((float)(var11));
			var10.z = 2.2F;
			var10.xRot = 0.3F;
			var10.zRot = -0.2F;
		}else{
			if (var2 instanceof SeaCowModel){
				SeaCowModel var8_1 = ((SeaCowModel)(var2));
				var8_1.head.xRot = (float)(Math.toRadians(90D));
			}else{
				return;
			}
		}
	}
}