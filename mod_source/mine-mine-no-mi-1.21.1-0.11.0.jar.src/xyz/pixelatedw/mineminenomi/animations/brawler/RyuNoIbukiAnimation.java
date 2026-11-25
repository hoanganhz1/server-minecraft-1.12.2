package xyz.pixelatedw.mineminenomi.animations.brawler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class RyuNoIbukiAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public RyuNoIbukiAnimation(AnimationId<RyuNoIbukiAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		var3.translate(0D,0.7D,0D);
		var3.mulPose(Axis.XP.rotationDegrees(20F));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var2.rightLeg.y = 2F;
		var2.rightLeg.z = -4F;
		var2.rightLeg.xRot = (float)(Math.toRadians(-20D));
		var2.leftLeg.y = 7F;
		var2.leftLeg.z = -7F;
		var2.leftLeg.xRot = (float)(Math.toRadians(50D));
		var2.leftArm.y = 2F;
		var2.leftArm.z = -2F;
		var2.leftArm.xRot = (float)(Math.toRadians(-40D));
		var2.rightArm.y = 2F;
		var2.rightArm.z = -2F;
		var2.rightArm.xRot = (float)(Math.toRadians(-40D));
	}
}