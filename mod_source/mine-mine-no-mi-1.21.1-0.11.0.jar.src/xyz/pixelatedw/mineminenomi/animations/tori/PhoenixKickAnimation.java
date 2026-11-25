package xyz.pixelatedw.mineminenomi.animations.tori;

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
import xyz.pixelatedw.mineminenomi.models.morphs.partials.PhoenixAssaultModel;

public class PhoenixKickAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public PhoenixKickAnimation(AnimationId<PhoenixKickAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = 0F;
		var2.rightLeg.zRot = var8;
		var2.rightLeg.yRot = var8;
		var2.rightLeg.xRot = var8;
		float var9 = 0F;
		var2.leftLeg.zRot = var9;
		var2.leftLeg.yRot = var9;
		var2.leftLeg.xRot = var9;
		var2.rightLeg.xRot = (((var2.rightLeg.xRot) * (0.5F)) - (3.1415927F)) + (2.3F);
		var2.rightLeg.yRot = (((var2.rightLeg.yRot) * (0.5F)) - (3.1415927F)) - (2.2F);
		var2.leftLeg.xRot = (((var2.leftLeg.xRot) * (0.5F)) - (3.1415927F)) + (2.5F);
		var2.leftLeg.xRot = (((var2.leftLeg.xRot) * (0.5F)) - (3.1415927F)) + (2.5F);
		if (var2 instanceof PhoenixAssaultModel){
			PhoenixAssaultModel var10 = ((PhoenixAssaultModel)(var2));
			var10.leftLeg.yRot = (((var10.leftLeg.yRot) * (0.5F)) - (3.1415927F)) + (2.1F);
			var10.leftLeg.zRot = (((var10.leftLeg.zRot) * (0.5F)) - (3.1415927F)) - (2.1F);
		}
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		var3.mulPose(Axis.XP.rotationDegrees(-45F));
		var3.mulPose(Axis.YP.rotationDegrees(-45F));
	}
}