package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractGorillaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.GorillaModel;

public class WeaponSlamAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public WeaponSlamAnimation(AnimationId<WeaponSlamAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		int var12 = (int)(((float)((int)(this.getTime()))) * (4.5F));
		var12 = (Mth.clamp(var12,0,50)) - (20);
		var3.mulPose(Axis.XP.rotationDegrees((float)(var12)));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if ((WyHelper.getDifferenceToFloor((Entity)(var1))) > (4D)){
			if (var2 instanceof GorillaModel){
				var2.rightArm.xRot = (float)(Math.toRadians(150D));
				var2.rightArm.zRot = (float)(Math.toRadians(0D));
				var2.leftArm.xRot = (float)(Math.toRadians(150D));
				var2.leftArm.zRot = (float)(Math.toRadians(0D));
			}else{
				var2.rightArm.xRot = (float)(Math.toRadians(150D));
				var2.rightArm.zRot = (float)(Math.toRadians(20D));
				var2.leftArm.xRot = (float)(Math.toRadians(150D));
				var2.leftArm.zRot = (float)(Math.toRadians(-20D));
			}
			var2.rightLeg.xRot = 1.2F;
			var2.rightLeg.yRot = -0.1F;
			var2.rightLeg.z = -1F;
			var2.leftLeg.xRot = 1.2F;
			var2.leftLeg.yRot = 0.1F;
			var2.leftLeg.z = -1F;
		}else{
			var2.rightArm.xRot = (float)(Math.toRadians(-50D));
			var2.rightArm.zRot = (float)(Math.toRadians(-20D));
			var2.leftArm.xRot = (float)(Math.toRadians(-50D));
			var2.leftArm.zRot = (float)(Math.toRadians(20D));
			var2.rightLeg.xRot = -1.2F;
			var2.rightLeg.yRot = 0.3F;
			var2.rightLeg.z = 1F;
			var2.leftLeg.xRot = -1.2F;
			var2.leftLeg.yRot = -0.3F;
			var2.leftLeg.z = 1F;
		}
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		double var8 = WyHelper.getDifferenceToFloor((Entity)(var1));
		if (var1 instanceof AbstractGorillaEntity){

		}else{
			var5.translate(-0.15D,-0.2D,-0.2D);
		}
		if ((var8) > (2D)){
			var5.mulPose(Axis.XP.rotationDegrees(-55F));
		}else{
			var5.mulPose(Axis.XP.rotationDegrees(-75F));
		}
		var5.mulPose(Axis.YP.rotationDegrees(-20F));
		var5.mulPose(Axis.ZP.rotationDegrees(-7F));
	}
}