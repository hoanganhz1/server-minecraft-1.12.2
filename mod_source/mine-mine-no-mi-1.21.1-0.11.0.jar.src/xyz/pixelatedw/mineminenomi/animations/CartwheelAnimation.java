package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class CartwheelAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public CartwheelAnimation(AnimationId<CartwheelAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::postAngles))));
	}
	public void postAngles(LivingEntity var1, HumanoidModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		var1.yBodyRot = var1.yHeadRot;
		float var12 = ((float)(this.getAnimationInitialDuration())) * (0.8F);
		float var13 = (Math.min((float)(this.getTime()),var12)) / (var12);
		float var14 = ((float)(this.getAnimationInitialDuration())) * (0.6F);
		float var15 = (Math.min((float)(this.getTime()),var14)) / (var14);
		float var16 = ((float)(this.getAnimationInitialDuration())) * (0.7F);
		float var17 = (Math.min((float)(this.getTime()),var16)) / (var16);
		if ((this.getAnimationCompletion()) >= (0.7F)){
			var16 = ((float)(this.getAnimationInitialDuration())) * (0.3F);
			var17 = ((float)(Math.max(((long)(this.getAnimationInitialDuration())) - (this.getTime()),0L))) / (var16);
		}
		if ((this.getAnimationCompletion()) >= (0.6F)){
			var14 = ((float)(this.getAnimationInitialDuration())) * (0.4F);
			var15 = ((float)(Math.max(((long)(this.getAnimationInitialDuration())) - (this.getTime()),0L))) / (var14);
		}
		float var18 = EasingFunctionHelper.easeInBack(Float.valueOf(var13));
		float var19 = EasingFunctionHelper.easeInCubic(Float.valueOf(var17));
		float var20 = EasingFunctionHelper.easeInSine(Float.valueOf(var15));
		var3.translate(0D,(0.7D) * ((double)(var19)),0D);
		var3.mulPose(Axis.XP.rotationDegrees((360F) * (var18)));
		var3.mulPose(Axis.YP.rotationDegrees(90F));
		var2.head.xRot = 0F;
		var2.rightLeg.xRot = 0F;
		var2.rightLeg.yRot = 0F;
		var2.rightLeg.zRot = (float)(Math.toRadians((double)((45F) * (var20))));
		var2.rightLeg.x = (-2F) + ((-1F) * (var20));
		var2.rightLeg.y = (12F) - ((2F) * (var20));
		var2.leftLeg.xRot = 0F;
		var2.leftLeg.yRot = 0F;
		var2.leftLeg.zRot = (float)(Math.toRadians((double)((-65F) * (var20))));
		var2.leftLeg.x = (2F) + ((1.5F) * (var20));
		var2.leftLeg.y = (12F) - ((1F) * (var20));
		var2.rightArm.xRot = 0F;
		var2.rightArm.yRot = 0F;
		var2.rightArm.zRot = (float)(Math.toRadians((double)((135F) * (var20))));
		var2.rightArm.y = (2F) + ((-3F) * (var20));
		var2.leftArm.xRot = 0F;
		var2.leftArm.yRot = 0F;
		var2.leftArm.zRot = (float)(Math.toRadians((double)((-135F) * (var20))));
		var2.leftArm.y = (2F) + ((-3F) * (var20));
		if (var2 instanceof PlayerModel){
			PlayerModel var21 = ((PlayerModel)(var2));
			var21.rightPants.copyFrom(var21.rightLeg);
			var21.leftPants.copyFrom(var21.leftLeg);
			var21.rightSleeve.copyFrom(var21.rightArm);
			var21.leftSleeve.copyFrom(var21.leftArm);
		}
	}
}