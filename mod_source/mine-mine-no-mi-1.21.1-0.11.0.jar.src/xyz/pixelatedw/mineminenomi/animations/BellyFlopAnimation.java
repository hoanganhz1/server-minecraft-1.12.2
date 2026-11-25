package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.GorillaModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.SeaCowModel;

public class BellyFlopAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
	public BellyFlopAnimation(AnimationId<BellyFlopAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		if ((var2 instanceof SeaCowModel) || (!(var2 instanceof HumanoidModel))){

		}else{
			var3.mulPose(Axis.XP.rotationDegrees(90F));
			var3.translate(0D,0D,-0.8D);
		}
	}
	public void angles(LivingEntity var1, EntityModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if (var2 instanceof HumanoidModel){
			HumanoidModel var8 = ((HumanoidModel)(var2));
			var8.head.xRot = 0F;
			var8.head.yRot = 0F;
			var8.head.zRot = 0F;
			var8.hat.copyFrom(var8.head);
			var8.rightArm.zRot = (float)(Math.toRadians(45D));
			var8.rightArm.xRot = (float)(Math.toRadians(8D));
			var8.leftArm.zRot = (float)(Math.toRadians(-45D));
			var8.leftArm.xRot = (float)(Math.toRadians(8D));
			var8.rightLeg.zRot = (float)(Math.toRadians(15D));
			var8.rightLeg.xRot = (float)(Math.toRadians(8D));
			var8.leftLeg.zRot = (float)(Math.toRadians(-15D));
			var8.leftLeg.xRot = (float)(Math.toRadians(8D));
			if (var2 instanceof GorillaModel){
				var8.rightArm.zRot = (float)(Math.toRadians(75D));
				var8.leftArm.zRot = (float)(Math.toRadians(-75D));
			}else{
				return;
			}
		}else{
			if (var2 instanceof SeaCowModel){
				SeaCowModel var8_1 = ((SeaCowModel)(var2));
				var8_1.leftFin.zRot = (float)(Math.toRadians(-60D));
				var8_1.leftFin2.zRot = (float)(Math.toRadians(0D));
				var8_1.rightFin.zRot = (float)(Math.toRadians(60D));
				var8_1.rightFin2.zRot = (float)(Math.toRadians(0D));
			}else{
				return;
			}
		}
	}
}