package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class UnicycleRidingAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public UnicycleRidingAnimation(AnimationId<UnicycleRidingAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = 10F;
		float var9 = 0F;
		float var10 = 12F;
		float var11 = 0F;
		double var12 = Math.abs((var1.getX()) - (var1.xOld));
		double var13 = Math.abs((var1.getZ()) - (var1.zOld));
		if ((((((var12) != (0D)) || ((var13) != (0D))) ? (1) : (0)) == (0)) && ((var1.zza) == (0F))){

		}else{
			float var14 = 0.0F;
			if (var1 instanceof Player){
				var14 = 7.5F;
				var8 = (11F) + ((((-(Mth.cos((var3) * (var14)))) * (12.9F)) * (var4)) / (1F));
				var9 = (((Mth.sin(((var3) * (var14)) + (3.1415927F))) * (1.9F)) * (var4)) / (1F);
				var10 = (11F) + ((((Mth.cos((var3) * (var14))) * (12.9F)) * (var4)) / (1F));
				var11 = (((-(Mth.sin(((var3) * (var14)) + (3.1415927F)))) * (1.9F)) * (var4)) / (1F);
			}else{
				var14 = 0.8F;
				var8 = (11F) + ((((-(Mth.cos((var3) * (var14)))) * (0.9F)) * (var4)) / (1F));
				var9 = (((Mth.sin(((var3) * (var14)) + (3.1415927F))) * (0.3F)) * (var4)) / (1F);
				var10 = (11F) + ((((Mth.cos((var3) * (var14))) * (0.9F)) * (var4)) / (1F));
				var11 = (((-(Mth.sin(((var3) * (var14)) + (3.1415927F)))) * (0.3F)) * (var4)) / (1F);
			}
		}
		var2.rightLeg.z = -0.5F;
		var2.rightLeg.y = var8;
		var2.rightLeg.x = -2.5F;
		var2.rightLeg.xRot = var9;
		var2.rightLeg.yRot = 0.2F;
		var2.rightLeg.zRot = 0.02F;
		var2.leftLeg.z = -0.5F;
		var2.leftLeg.y = var10;
		var2.leftLeg.x = 2.5F;
		var2.leftLeg.xRot = var11;
		var2.leftLeg.yRot = -0.2F;
		var2.leftLeg.zRot = -0.02F;
	}
}