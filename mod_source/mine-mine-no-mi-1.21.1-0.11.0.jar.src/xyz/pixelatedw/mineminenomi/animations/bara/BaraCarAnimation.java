package xyz.pixelatedw.mineminenomi.animations.bara;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BaraCarAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public BaraCarAnimation(AnimationId<BaraCarAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::postAngles))));
	}
	public void postAngles(LivingEntity var1, HumanoidModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		var2.head.y = 19F;
		var2.head.z = -1.5F;
		var2.body.z = -7F;
		var2.body.y = 20F;
		var2.body.xRot = (float)(Math.toRadians(90D));
		var2.leftLeg.x = 4F;
		var2.leftLeg.y = 20F;
		var2.leftLeg.z = 4F;
		var2.leftLeg.xRot = (float)(Math.toRadians(100D));
		var2.leftLeg.yRot = (float)(Math.toRadians(15D));
		var2.leftLeg.zRot = (float)(Math.toRadians(10D));
		var2.rightLeg.x = -4F;
		var2.rightLeg.y = 20F;
		var2.rightLeg.z = 4F;
		var2.rightLeg.xRot = (float)(Math.toRadians(100D));
		var2.rightLeg.yRot = (float)(Math.toRadians(-15D));
		var2.rightLeg.zRot = (float)(Math.toRadians(-10D));
		var2.leftArm.y = 20F;
		var2.leftArm.z = -6F;
		var2.leftArm.xRot = (float)(Math.toRadians(-75D));
		var2.rightArm.y = 20F;
		var2.rightArm.z = -6F;
		var2.rightArm.xRot = (float)(Math.toRadians(-75D));
		if (var2 instanceof PlayerModel){
			PlayerModel var12 = ((PlayerModel)(var2));
			var12.rightSleeve.copyFrom(var12.rightArm);
			var12.leftSleeve.copyFrom(var12.leftArm);
			var12.rightPants.copyFrom(var12.rightLeg);
			var12.leftPants.copyFrom(var12.leftLeg);
		}
	}
}