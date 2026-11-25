package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class SpecialFlyAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public SpecialFlyAnimation(AnimationId<SpecialFlyAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		double var8 = Math.abs((var1.xo) - (var1.getX()));
		double var9 = Math.abs((var1.zo) - (var1.getZ()));
		float var10 = 0F;
		if ((!((var8) >= (0.01D))) && (!((var9) >= (0.01D)))){

		}else{
			var10 = (float)(Math.toRadians(45D));
			var2.rightLeg.y = 10F;
			var2.rightLeg.z = 8F;
			var2.leftLeg.z = 3F;
			var2.rightArm.z = 2F;
			var2.leftArm.z = 2F;
		}
		if ((!((var8) >= (0.5D))) && (!((var9) >= (0.5D)))){

		}else{
			var10 = (float)(Math.toRadians(60D));
			var2.rightLeg.y = 8F;
			var2.rightLeg.z = 10F;
			var2.leftLeg.y = 8F;
			var2.rightArm.y = 1F;
			var2.leftArm.y = 1F;
			var2.rightArm.z = 2.5F;
			var2.leftArm.z = 2.5F;
		}
		var2.body.xRot = var10;
		if ((var1.attackAnim) <= (0F)){
			var2.rightArm.xRot = var10;
			var2.leftArm.xRot = var10;
		}
		if (var1.isCrouching()){
			var2.rightArm.y = 4.5F;
			var2.leftArm.y = 4.5F;
		}
		var2.rightLeg.xRot = var10;
		var2.leftLeg.xRot = var10;
		var2.rightLeg.visible = false;
		var2.leftLeg.visible = false;
		if (var2 instanceof PlayerModel){
			PlayerModel var11 = ((PlayerModel)(var2));
			var11.leftPants.visible = false;
			var11.rightPants.visible = false;
		}
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		double var12 = Math.abs((var1.xo) - (var1.getX()));
		double var13 = Math.abs((var1.zo) - (var1.getZ()));
		double var14 = 0D;
		if ((!((var12) >= (0.01D))) && (!((var13) >= (0.01D)))){

		}else{
			var14 = 0.1D;
		}
		if ((!((var12) >= (0.5D))) && (!((var13) >= (0.5D)))){

		}else{
			var14 = 0.2D;
		}
		var3.translate(0D,var14,0D);
	}
}