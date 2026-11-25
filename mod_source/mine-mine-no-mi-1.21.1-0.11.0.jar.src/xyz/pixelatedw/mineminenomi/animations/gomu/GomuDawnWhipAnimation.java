package xyz.pixelatedw.mineminenomi.animations.gomu;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.animations.BodyRotateAnimation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class GomuDawnWhipAnimation extends BodyRotateAnimation {
	public GomuDawnWhipAnimation(AnimationId<GomuDawnWhipAnimation> var1) {
		super((AnimationId)(var1),75F);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
	}
	public void angles(LivingEntity var1, AgeableListModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if (var2 instanceof HumanoidModel){
			HumanoidModel var8 = ((HumanoidModel)(var2));
			var8.leftArm.zRot = (float)(Math.toRadians(-90D));
			var8.rightArm.zRot = (float)(Math.toRadians(90D));
			var8.rightLeg.zRot = (float)(Math.toRadians(90D));
			var8.leftLeg.zRot = (float)(Math.toRadians(-90D));
		}
	}
	public void setup(LivingEntity var1, AgeableListModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		float var12 = (float)((Math.sin(((double)(((float)(this.getTime())) * (0.1F))) + (3.141592653589793D))) * (16D));
		var3.mulPose(Axis.YP.rotation(var12));
	}
}