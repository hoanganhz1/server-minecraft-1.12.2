package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class SpinToWinAnimation extends BodyRotateAnimation {
	public SpinToWinAnimation(AnimationId<SpinToWinAnimation> var1, float var2) {
		super((AnimationId)(var1),var2);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void setup(LivingEntity var1, AgeableListModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		super.setup(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10,var11);
		var3.translate(0D,0D,0.4000000059604645D);
	}
	private void angles(LivingEntity var1, AgeableListModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if (var2 instanceof HumanoidModel){
			HumanoidModel var8 = ((HumanoidModel)(var2));
			var8.rightArm.xRot = (float)(Math.toRadians(-90D));
			var8.rightArm.zRot = (float)(Math.toRadians(20D));
			var8.rightArm.yRot = (float)(Math.toRadians(-20D));
			var8.leftArm.xRot = (float)(Math.toRadians(-90D));
			var8.leftArm.zRot = (float)(Math.toRadians(-20D));
			var8.leftArm.yRot = (float)(Math.toRadians(20D));
			var8.body.y = 0.5F;
			var8.body.xRot = (float)(Math.toRadians(-15D));
			var8.rightLeg.z = -3F;
			var8.rightLeg.xRot = (float)(Math.toRadians(-15D));
			var8.leftLeg.z = -3F;
			var8.leftLeg.xRot = (float)(Math.toRadians(-15D));
		}
	}
	private void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		var5.translate(-0.15D,-0.1D,-0.2D);
		var5.mulPose(Axis.XP.rotationDegrees(-85F));
		var5.mulPose(Axis.YP.rotationDegrees(20F));
		var5.mulPose(Axis.ZP.rotationDegrees(-16F));
	}
}