package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ShootSelfForwardAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public ShootSelfForwardAnimation(AnimationId<ShootSelfForwardAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var2.head.xRot = (float)(Math.toRadians(0D));
		var2.head.yRot = (float)(Math.toRadians(0D));
		var2.leftLeg.yRot = 0F;
		var2.leftLeg.xRot = 0F;
		var2.rightLeg.xRot = 0F;
		var2.rightLeg.yRot = 0F;
		var2.leftArm.yRot = 0F;
		var2.leftArm.xRot = 0F;
		var2.rightArm.xRot = 0F;
		var2.rightArm.yRot = 0F;
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		var3.mulPose(Axis.XP.rotationDegrees((Mth.lerp(var6,var1.xRotO,var1.getXRot())) + (90F)));
	}
}