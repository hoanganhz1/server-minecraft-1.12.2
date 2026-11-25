package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class DodgeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	// Fields
	private float dodgeDistance;
	private float dodgeRotation;

	public DodgeAnimation(AnimationId<DodgeAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::postAngles))));
	}
	public void start(LivingEntity var1, int var2) {
		super.start(var1,var2);
		int var3 = (var1.getRandom().nextBoolean()) ? (1) : (-1);
		this.dodgeDistance = ((0.3F) + ((var1.getRandom().nextFloat()) * (0.5F))) * ((float)(var3));
		this.dodgeRotation = ((50F) + ((var1.getRandom().nextFloat()) * (60F))) * ((float)(var3));
	}
	public void postAngles(LivingEntity var1, HumanoidModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		var1.yBodyRot = var1.yHeadRot;
		float var12 = ((float)(this.getAnimationInitialDuration())) * (0.5F);
		float var13 = (Math.min((float)(this.getTime()),var12)) / (var12);
		if ((this.getAnimationCompletion()) >= (0.8F)){
			var12 = ((float)(this.getAnimationInitialDuration())) * (0.3F);
			var13 = ((float)(Math.max(((long)(this.getAnimationInitialDuration())) - (this.getTime()),0L))) / (var12);
		}
		float var14 = EasingFunctionHelper.easeOutCubic(Float.valueOf(var13));
		float var15 = EasingFunctionHelper.easeOutBack(Float.valueOf(var13));
		var3.translate((this.dodgeDistance) * (var15),0F,0F);
		var3.mulPose(Axis.YP.rotationDegrees((this.dodgeRotation) * (var14)));
	}
}