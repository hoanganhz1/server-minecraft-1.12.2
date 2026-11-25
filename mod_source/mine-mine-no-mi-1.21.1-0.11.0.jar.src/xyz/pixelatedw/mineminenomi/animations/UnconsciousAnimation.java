package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class UnconsciousAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
	public UnconsciousAnimation(AnimationId<UnconsciousAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::postAngles))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void start(LivingEntity var1, int var2) {
		super.start(var1,var2);
		if ((!(var1 instanceof Player)) && (!(var1 instanceof Mob))){

		}else{
			if ((((var1.getRandom().nextInt(10)) < (1)) ? (1) : (0)) != (0)){
				this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(UnconsciousYamchaAnimation::postAngles))));
				this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(UnconsciousYamchaAnimation::angles))));
			}else{
				return;
			}
		}
	}
	private void angles(LivingEntity var1, EntityModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if (var1.isPassenger()){
			if (var2 instanceof HumanoidModel){
				HumanoidModel var8 = ((HumanoidModel)(var2));
				var8.head.y = 1.5F;
				var8.head.xRot = (float)(Math.toRadians(50D));
				ModelPart var9 = var8.rightArm;
				var9.xRot = (var9.xRot) + (-0.62831855F);
				ModelPart var10 = var8.leftArm;
				var10.xRot = (var10.xRot) + (-0.62831855F);
				var8.rightLeg.xRot = -1.4137167F;
				var8.rightLeg.yRot = 0.31415927F;
				var8.rightLeg.zRot = 0.07853982F;
				var8.leftLeg.xRot = -1.4137167F;
				var8.leftLeg.yRot = -0.31415927F;
				var8.leftLeg.zRot = -0.07853982F;
			}
			return;
		}else{
			if (var2 instanceof HumanoidModel){
				HumanoidModel var8_1 = ((HumanoidModel)(var2));
				var8_1.leftLeg.xRot = 0F;
				var8_1.rightLeg.xRot = 0F;
				var8_1.leftArm.xRot = 0F;
				var8_1.leftArm.zRot = -0.05F;
				var8_1.rightArm.xRot = 0F;
				var8_1.rightArm.zRot = 0.05F;
				var8_1.head.xRot = 0F;
				var8_1.head.yRot = 0F;
				var8_1.head.zRot = 0F;
			}
			return;
		}
	}
	private void postAngles(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		if (var1.isPassenger()){
			Entity var12 = var1.getVehicle();
			var1.setYRot(var12.getYRot());
			var1.setXRot(var12.getXRot());
			var3.mulPose(Axis.YP.rotationDegrees((var1.getYRot()) + (90F)));
			return;
		}else{
			var3.mulPose(Axis.XP.rotationDegrees(90F));
			var3.translate(0D,-0.5D,-1.4D);
			return;
		}
	}
}