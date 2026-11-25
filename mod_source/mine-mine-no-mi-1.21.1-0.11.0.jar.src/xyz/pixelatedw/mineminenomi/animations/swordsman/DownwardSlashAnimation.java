package xyz.pixelatedw.mineminenomi.animations.swordsman;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class DownwardSlashAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	// Fields
	private static final float DEFAULT_TIME = 20F;
	private static final float PHASE_PER = 0.6F;

	public DownwardSlashAnimation(AnimationId<DownwardSlashAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = Math.min((float)((this.getAnimationInitialDuration()) + (4)),20F);
		if ((var8) < (0F)){
			var8 = 20F;
		}
		float var9 = Math.min(((float)(this.getTime())) / (var8),1F);
		float var10 = (var8) * (0.6F);
		float var11 = (Math.min((float)(this.getTime()),var10)) / (var10);
		if ((var9) > (0.6F)){
			var10 = (var8) * (0.6F);
			var11 = (Math.max((var8) - ((float)(this.getTime())),0F)) / (var10);
		}
		float var12 = EasingFunctionHelper.easeInOutCubic(Float.valueOf(var11));
		var2.rightArm.x = (-5F) + ((1F) * (var12));
		var2.rightArm.y = (2F) + (var12);
		var2.rightArm.z = (-5F) * (var12);
		var2.rightArm.xRot = ((float)(Math.toRadians(-170D))) * (var12);
		var2.rightArm.zRot = ((float)(Math.toRadians(10D))) * (var12);
		var2.leftArm.x = (5F) + ((-1F) * (var12));
		var2.leftArm.y = (2F) + (var12);
		var2.leftArm.z = (-5F) * (var12);
		var2.leftArm.xRot = ((float)(Math.toRadians(-170D))) * (var12);
		var2.leftArm.zRot = ((float)(Math.toRadians(-10D))) * (var12);
		var2.head.y = (3F) * (var12);
		var2.head.z = (-1F) * (var12);
		var2.body.z = (-2F) * (var12);
		var2.body.y = (3F) * (var12);
		var2.body.xRot = ((float)(Math.toRadians(5D))) * (var12);
		var2.rightLeg.z = (-2F) * (var12);
		var2.rightLeg.xRot = ((float)(Math.toRadians(-5D))) * (var12);
		var2.leftLeg.z = (-0.5F) * (var12);
		var2.leftLeg.xRot = ((float)(Math.toRadians(15D))) * (var12);
		var2.leftLeg.yRot = ((float)(Math.toRadians(-10D))) * (var12);
		var2.leftLeg.zRot = ((float)(Math.toRadians(-5D))) * (var12);
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		float var8 = Math.min((float)(this.getAnimationInitialDuration()),20F);
		if ((var8) < (0F)){
			var8 = 20F;
		}
		float var9 = Math.min(((float)(this.getTime())) / (var8),1F);
		float var10 = 25F;
		float var11 = 1F;
		if ((var9) >= (1F)){
			return;
		}else{
			if ((var9) > (0.7F)){
				var10 = -25F;
				var11 = EasingFunctionHelper.easeOutCubic(Float.valueOf((var9) - (0.7F)));
			}
			var5.translate(-0.15D,0.2D,0.05D);
			var5.mulPose(Axis.XP.rotationDegrees((var10) * (var11)));
			var5.mulPose(Axis.YP.rotationDegrees(-5F));
			return;
		}
	}
}