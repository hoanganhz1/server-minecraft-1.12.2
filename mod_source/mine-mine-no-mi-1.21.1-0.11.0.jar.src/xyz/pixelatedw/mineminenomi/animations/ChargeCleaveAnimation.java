package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ChargeCleaveAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public ChargeCleaveAnimation(AnimationId<ChargeCleaveAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = 50F;
		float var9 = (float)(this.getTime());
		float var10 = 5F;
		double var11 = -200D;
		double var12 = 20D;
		double var13 = -130D;
		double var14 = 110D;
		if ((var9) > (var10)){
			double var15 = (double)(((var9) - (var10)) * (var8));
			var11 = Mth.clamp((-200D) + (var15),-200D,-20D);
			var12 = Mth.clamp((20D) - ((var15) / (3D)),-10D,20D);
			var13 = Mth.clamp((-130D) + ((var15) / (2D)),-130D,-50D);
			var14 = Mth.clamp((110D) - ((var15) / (3D)),50D,110D);
		}
		ModelPart var15_1 = var2.rightArm;
		var15_1.z = (var15_1.z) + (0F);
		ModelPart var16 = var2.rightArm;
		var16.xRot = (float)(((double)(var16.xRot)) + (Math.toRadians(var11)));
		ModelPart var17 = var2.rightArm;
		var17.yRot = (float)(((double)(var17.yRot)) + (Math.toRadians(var12)));
		ModelPart var18 = var2.leftArm;
		var18.x = (var18.x) - (2F);
		ModelPart var19 = var2.leftArm;
		var19.z = (var19.z) - (2F);
		ModelPart var20 = var2.leftArm;
		var20.xRot = (float)(((double)(var20.xRot)) + (Math.toRadians(var13)));
		ModelPart var21 = var2.leftArm;
		var21.yRot = (float)(((double)(var21.yRot)) + (Math.toRadians(var14)));
		ModelPart var22 = var2.body;
		var22.yRot = (float)(((double)(var22.yRot)) + (Math.toRadians(30D)));
		ModelPart var23 = var2.rightLeg;
		var23.z = (var23.z) + (1F);
		ModelPart var24 = var2.rightLeg;
		var24.yRot = (float)(((double)(var24.yRot)) + (Math.toRadians(40D)));
		ModelPart var25 = var2.leftLeg;
		var25.z = (var25.z) - (1F);
		ModelPart var26 = var2.leftLeg;
		var26.yRot = (float)(((double)(var26.yRot)) - (Math.toRadians(20D)));
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		var5.translate(-0.1D,-0.2D,-0.05D);
		var5.mulPose(Axis.XP.rotationDegrees(-75F));
	}
}