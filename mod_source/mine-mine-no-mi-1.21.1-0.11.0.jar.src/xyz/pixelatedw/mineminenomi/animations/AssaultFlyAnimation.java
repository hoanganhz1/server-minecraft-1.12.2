package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.morphs.partials.PhoenixAssaultModel;

public class AssaultFlyAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	// Fields
	private static final double THRESHOLD_1 = 0.2D;
	private static final double THRESHOLD_2 = 0.6D;

	public AssaultFlyAnimation(AnimationId<AssaultFlyAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		double var8 = Math.abs((var1.xo) - (var1.getX()));
		double var9 = Math.abs((var1.zo) - (var1.getZ()));
		Vec3 var10 = var1.getLookAngle();
		Vec3 var11 = new Vec3(var1.xo,var1.yo,var1.zo);
		double var12 = var1.position().subtract(var11).normalize().dot(var10);
		float var13 = 0F;
		if ((var12) > (0D)){
			if ((!((var8) >= (0.2D))) && (!((var9) >= (0.2D)))){

			}else{
				if (var2 instanceof PhoenixAssaultModel){
					PhoenixAssaultModel var14 = ((PhoenixAssaultModel)(var2));
					float var15 = -0.5F;
					var14.lowerRightTail.xRot = var15;
					var14.lowerLeftTail.xRot = var15;
					var14.upperRightTail.xRot = var15;
					var14.upperLeftTail.xRot = var15;
				}
				var13 = (float)(Math.toRadians(45D));
				float var14_1 = 8F;
				var2.leftLeg.y = var14_1;
				var2.rightLeg.y = var14_1;
				float var15_1 = 8F;
				var2.leftLeg.z = var15_1;
				var2.rightLeg.z = var15_1;
				float var16 = (var13) + (0.2F);
				var2.leftLeg.xRot = var16;
				var2.rightLeg.xRot = var16;
			}
			if ((!((var8) >= (0.6D))) && (!((var9) >= (0.6D)))){

			}else{
				if (var2 instanceof PhoenixAssaultModel){
					PhoenixAssaultModel var14_2 = ((PhoenixAssaultModel)(var2));
					float var15_2 = -0.8F;
					var14_2.lowerRightTail.xRot = var15_2;
					var14_2.lowerLeftTail.xRot = var15_2;
					var14_2.upperRightTail.xRot = var15_2;
					var14_2.upperLeftTail.xRot = var15_2;
				}
				var13 = (float)(Math.toRadians(60D));
				float var14_3 = 5.8F;
				var2.leftLeg.y = var14_3;
				var2.rightLeg.y = var14_3;
				float var15_3 = 10F;
				var2.leftLeg.z = var15_3;
				var2.rightLeg.z = var15_3;
				float var16_1 = (var13) + (0.3F);
				var2.leftLeg.xRot = var16_1;
				var2.rightLeg.xRot = var16_1;
			}
		}else{
			if ((var12) < (0D)){
				if ((!((var8) >= (0.2D))) && (!((var9) >= (0.2D)))){

				}else{
					var13 = (float)(Math.toRadians(-45D));
					float var14_4 = 8F;
					var2.leftLeg.y = var14_4;
					var2.rightLeg.y = var14_4;
					float var15_4 = -8F;
					var2.leftLeg.z = var15_4;
					var2.rightLeg.z = var15_4;
					float var16_2 = (var13) - (0.2F);
					var2.leftLeg.xRot = var16_2;
					var2.rightLeg.xRot = var16_2;
				}
				if ((!((var8) >= (0.6D))) && (!((var9) >= (0.6D)))){

				}else{
					var13 = (float)(Math.toRadians(-60D));
					float var14_5 = 5.8F;
					var2.leftLeg.y = var14_5;
					var2.rightLeg.y = var14_5;
					float var15_5 = -10F;
					var2.leftLeg.z = var15_5;
					var2.rightLeg.z = var15_5;
					float var16_3 = (var13) - (0.3F);
					var2.leftLeg.xRot = var16_3;
					var2.rightLeg.xRot = var16_3;
				}
			}
		}
		var2.body.xRot = var13;
		if ((var1.attackAnim) <= (0F)){
			var2.rightArm.xRot = var13;
			var2.leftArm.xRot = var13;
		}
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		double var12 = Math.abs((var1.xo) - (var1.getX()));
		double var13 = Math.abs((var1.zo) - (var1.getZ()));
		double var14 = 0D;
		if ((!((var12) >= (0.2D))) && (!((var13) >= (0.2D)))){

		}else{
			var14 = 0.3D;
		}
		if ((!((var12) >= (0.6D))) && (!((var13) >= (0.6D)))){

		}else{
			var14 = 0.4D;
		}
		var3.translate(0D,var14,0D);
	}
}