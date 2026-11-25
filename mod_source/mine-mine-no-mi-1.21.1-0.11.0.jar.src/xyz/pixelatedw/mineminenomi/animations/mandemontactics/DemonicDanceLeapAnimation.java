package xyz.pixelatedw.mineminenomi.animations.mandemontactics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class DemonicDanceLeapAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public DemonicDanceLeapAnimation(AnimationId<DemonicDanceLeapAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		if (!(this.isInAir(var1))){
			var3.mulPose(Axis.XP.rotationDegrees(30F));
		}
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if (this.isInAir(var1)){
			var2.rightArm.xRot = (float)(Math.toRadians(180D));
			var2.rightArm.zRot = (float)(Math.toRadians(-10D));
			var2.rightLeg.xRot = 1.2F;
			var2.rightLeg.yRot = -0.1F;
			var2.rightLeg.z = -1F;
			var2.leftLeg.xRot = 1.2F;
			var2.leftLeg.yRot = 0.1F;
			var2.leftLeg.z = -1F;
		}else{
			var2.rightArm.xRot = (float)(Math.toRadians(-90D));
			var2.rightLeg.xRot = -1.2F;
			var2.rightLeg.yRot = 0.3F;
			var2.rightLeg.z = 1F;
			var2.leftLeg.xRot = 1.2F;
			var2.leftLeg.yRot = 0.1F;
			var2.leftLeg.z = -1F;
		}
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		if ((var4) == (HumanoidArm.RIGHT)){
			var5.translate(0D,0D,0.15D);
			var5.mulPose(Axis.YP.rotationDegrees(180F));
		}
	}
	private boolean isInAir(LivingEntity var1) {
		double var2 = WyHelper.getDifferenceToFloor((Entity)(var1));
		return ((this.getTime()) <= (5L)) || ((var2) > (0.800000011920929D));
	}
}