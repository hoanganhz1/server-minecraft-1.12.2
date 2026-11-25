package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
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

public class PointWeaponAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public PointWeaponAnimation(AnimationId<PointWeaponAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		ModelPart var8 = var2.rightArm;
		var8.z = (var8.z) + (3F);
		ModelPart var9 = var2.rightArm;
		var9.xRot = (float)(((double)(var9.xRot)) + (Math.toRadians(-70D)));
		var2.rightArm.yRot = 0F;
		ModelPart var10 = var2.leftArm;
		var10.xRot = (float)(((double)(var10.xRot)) + (Math.toRadians(-90D)));
		ModelPart var11 = var2.leftArm;
		var11.yRot = (float)(((double)(var11.yRot)) + (Math.toRadians(60D)));
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		var5.translate(0D,-0.2D,-0.05D);
		var5.mulPose(Axis.XP.rotationDegrees(-75F));
	}
}