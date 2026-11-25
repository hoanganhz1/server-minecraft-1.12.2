package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class UnconsciousYamchaAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
	public UnconsciousYamchaAnimation(AnimationId<UnconsciousYamchaAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(UnconsciousYamchaAnimation::postAngles))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(UnconsciousYamchaAnimation::angles))));
	}
	public static void angles(LivingEntity var0, EntityModel<LivingEntity> var1, float var2, float var3, float var4, float var5, float var6) {
		if (var0.isPassenger()){
			if (var1 instanceof HumanoidModel){
				HumanoidModel var7 = ((HumanoidModel)(var1));
				var7.head.y = 1.5F;
				var7.head.xRot = (float)(Math.toRadians(50D));
				ModelPart var8 = var7.rightArm;
				var8.xRot = (var8.xRot) + (-0.62831855F);
				ModelPart var9 = var7.leftArm;
				var9.xRot = (var9.xRot) + (-0.62831855F);
				var7.rightLeg.xRot = -1.4137167F;
				var7.rightLeg.yRot = 0.31415927F;
				var7.rightLeg.zRot = 0.07853982F;
				var7.leftLeg.xRot = -1.4137167F;
				var7.leftLeg.yRot = -0.31415927F;
				var7.leftLeg.zRot = -0.07853982F;
			}
			return;
		}else{
			if (var1 instanceof HumanoidModel){
				HumanoidModel var7_1 = ((HumanoidModel)(var1));
				var7_1.leftLeg.x = 1F;
				var7_1.leftLeg.xRot = (float)(Math.toRadians(5D));
				var7_1.leftLeg.yRot = (float)(Math.toRadians(90D));
				var7_1.rightLeg.x = -4F;
				var7_1.rightLeg.y = 10F;
				var7_1.rightLeg.xRot = (float)(Math.toRadians(10D));
				var7_1.rightLeg.yRot = (float)(Math.toRadians(90D));
				var7_1.leftArm.x = 1.5F;
				var7_1.leftArm.z = -1.2F;
				var7_1.leftArm.y = 5F;
				var7_1.leftArm.xRot = (float)(Math.toRadians(-75D));
				var7_1.leftArm.yRot = (float)(Math.toRadians(90D));
				var7_1.rightArm.z = -1F;
				var7_1.rightArm.xRot = (float)(Math.toRadians(-160D));
				var7_1.body.yRot = (float)(Math.toRadians(30D));
				var7_1.body.zRot = (float)(Math.toRadians(5D));
				var7_1.head.xRot = 0F;
				var7_1.head.yRot = (float)(Math.toRadians(40D));
				var7_1.head.zRot = 0F;
			}
			return;
		}
	}
	public static void postAngles(LivingEntity var0, EntityModel<LivingEntity> var1, PoseStack var2, MultiBufferSource var3, int var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if (var0.isPassenger()){
			Entity var11 = var0.getVehicle();
			var0.setYRot(var11.getYRot());
			var0.setXRot(var11.getXRot());
			var2.mulPose(Axis.YP.rotationDegrees((var0.getYRot()) + (90F)));
			return;
		}else{
			var2.mulPose(Axis.XP.rotationDegrees(90F));
			var2.translate(0D,-0.5D,-1.35D);
			return;
		}
	}
}