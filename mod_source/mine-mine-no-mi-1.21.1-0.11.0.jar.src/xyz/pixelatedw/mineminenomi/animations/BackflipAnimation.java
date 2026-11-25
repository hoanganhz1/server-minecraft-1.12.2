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

public class BackflipAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public BackflipAnimation(AnimationId<BackflipAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::postAngles))));
	}
	public void postAngles(LivingEntity var1, HumanoidModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		var1.yBodyRot = var1.yHeadRot;
		float var12 = ((float)(this.getAnimationInitialDuration())) * (0.8F);
		float var13 = (Math.min((float)(this.getTime()),var12)) / (var12);
		float var14 = ((float)(this.getAnimationInitialDuration())) * (0.6F);
		float var15 = (Math.min((float)(this.getTime()),var14)) / (var14);
		float var16 = ((float)(this.getAnimationInitialDuration())) * (0.4F);
		float var17 = (Math.min((float)(this.getTime()),var16)) / (var16);
		if ((this.getAnimationCompletion()) >= (0.4F)){
			var16 = ((float)(this.getAnimationInitialDuration())) * (0.6F);
			var17 = ((float)(Math.max(((long)(this.getAnimationInitialDuration())) - (this.getTime()),0L))) / (var16);
		}
		if ((this.getAnimationCompletion()) >= (0.6F)){
			var14 = ((float)(this.getAnimationInitialDuration())) * (0.4F);
			var15 = ((float)(Math.max(((long)(this.getAnimationInitialDuration())) - (this.getTime()),0L))) / (var14);
		}
		float var18 = EasingFunctionHelper.easeInBack(Float.valueOf(var13));
		float var19 = EasingFunctionHelper.easeInCubic(Float.valueOf(var17));
		float var20 = EasingFunctionHelper.easeInSine(Float.valueOf(var15));
		var3.translate(0D,(0.5D) * ((double)(var19)),0D);
		var3.mulPose(Axis.XN.rotationDegrees((360F) * (var18)));
		var2.head.xRot = 0F;
		var2.rightLeg.xRot = (0.25F) - ((float)(Math.toRadians((double)((80F) * (var20)))));
		var2.rightLeg.yRot = 0F;
		var2.rightLeg.zRot = 0F;
		var2.leftLeg.xRot = (float)(Math.toRadians((double)((50F) * (var20))));
		var2.leftLeg.yRot = 0F;
		var2.leftLeg.zRot = 0F;
		var2.rightArm.xRot = (float)(Math.toRadians((double)((50F) * (var20))));
		var2.rightArm.yRot = 0F;
		var2.rightArm.zRot = 0F;
		var2.leftArm.xRot = (0.25F) - ((float)(Math.toRadians((double)((80F) * (var20)))));
		var2.leftArm.yRot = 0F;
		var2.leftArm.zRot = 0F;
		if (var2 instanceof PlayerModel){
			PlayerModel var21 = ((PlayerModel)(var2));
			var21.rightPants.copyFrom(var21.rightLeg);
			var21.leftPants.copyFrom(var21.leftLeg);
			var21.rightSleeve.copyFrom(var21.rightArm);
			var21.leftSleeve.copyFrom(var21.leftArm);
		}
	}
}