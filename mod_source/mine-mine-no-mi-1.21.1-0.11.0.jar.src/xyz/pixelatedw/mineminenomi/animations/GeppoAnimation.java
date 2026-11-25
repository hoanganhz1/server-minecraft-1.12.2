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

public class GeppoAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public GeppoAnimation(AnimationId<GeppoAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::postAngles))));
	}
	public void postAngles(LivingEntity var1, HumanoidModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		float var12 = ((float)(this.getAnimationInitialDuration())) * (0.7F);
		float var13 = (Math.min((float)(this.getTime()),var12)) / (var12);
		float var14 = ((float)(this.getAnimationInitialDuration())) * (0.4F);
		float var15 = (Math.min((float)(this.getTime()),var14)) / (var14);
		float var16 = EasingFunctionHelper.easeOutCubic(Float.valueOf(var13));
		float var17 = EasingFunctionHelper.easeOutCubic(Float.valueOf(var15));
		var2.head.xRot = -0.4F;
		var2.rightLeg.xRot = 0F;
		var2.rightLeg.z = (-4F) * (var16);
		var2.rightLeg.y = (2F) + ((7F) * (var16));
		var2.leftLeg.xRot = 0F;
		var2.leftLeg.y = (9F) + ((2F) * (var16));
		var2.rightArm.xRot = 0.5F;
		var2.rightArm.zRot = 0.2F;
		var2.leftArm.xRot = 0.5F;
		var2.leftArm.zRot = -0.2F;
		if (var2 instanceof PlayerModel){
			PlayerModel var18 = ((PlayerModel)(var2));
			var18.hat.copyFrom(var18.head);
			var18.rightSleeve.copyFrom(var18.rightArm);
			var18.leftSleeve.copyFrom(var18.leftArm);
			var18.rightPants.copyFrom(var18.rightLeg);
			var18.leftPants.copyFrom(var18.leftLeg);
		}
		var3.mulPose(Axis.XP.rotationDegrees((50F) * (var17)));
	}
}