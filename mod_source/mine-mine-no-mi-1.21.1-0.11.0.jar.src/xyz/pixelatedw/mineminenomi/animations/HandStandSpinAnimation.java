package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class HandStandSpinAnimation extends BodyRotateAnimation {
	public HandStandSpinAnimation(AnimationId<HandStandSpinAnimation> var1) {
		super((AnimationId)(var1),35F);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, AgeableListModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if (var2 instanceof HumanoidModel){
			HumanoidModel var8 = ((HumanoidModel)(var2));
			var8.leftArm.xRot = (float)(Math.toRadians(180D));
			var8.leftArm.zRot = (float)(Math.toRadians(10D));
			var8.rightArm.xRot = (float)(Math.toRadians(180D));
			var8.rightArm.zRot = (float)(Math.toRadians(-10D));
			var8.rightLeg.zRot = (float)(Math.toRadians(90D));
			var8.leftLeg.zRot = (float)(Math.toRadians(-90D));
		}
	}
	public void setup(LivingEntity var1, AgeableListModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		if (var1 instanceof Player){
			var3.translate(0F,1F,0F);
		}
		var3.mulPose(Axis.XP.rotationDegrees(180F));
		super.setup(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10,var11);
	}
}