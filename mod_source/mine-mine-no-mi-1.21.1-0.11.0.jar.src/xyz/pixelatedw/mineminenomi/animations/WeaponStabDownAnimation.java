package xyz.pixelatedw.mineminenomi.animations;

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

public class WeaponStabDownAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public WeaponStabDownAnimation(AnimationId<WeaponStabDownAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		var2.rightArm.xRot = (float)(Math.toRadians(-70D));
		var2.rightArm.zRot = (float)(Math.toRadians(-20D));
		var2.rightArm.yRot = (float)(Math.toRadians(-20D));
		var2.leftArm.xRot = (float)(Math.toRadians(-70D));
		var2.leftArm.zRot = (float)(Math.toRadians(20D));
		var2.leftArm.yRot = (float)(Math.toRadians(20D));
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		var5.translate(-0.02D,-0.3D,-0.1D);
		var5.mulPose(Axis.ZP.rotationDegrees(192F));
		var5.mulPose(Axis.YP.rotationDegrees(30F));
		var5.mulPose(Axis.XP.rotationDegrees(-5F));
	}
}