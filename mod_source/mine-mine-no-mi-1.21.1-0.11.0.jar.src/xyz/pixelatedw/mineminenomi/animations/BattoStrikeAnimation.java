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

public class BattoStrikeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public BattoStrikeAnimation(AnimationId<BattoStrikeAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = (70F) - (((float)(this.getTime())) * (20F));
		var8 = Mth.clamp(var8,-50F,50F);
		var2.rightArm.x = -4F;
		var2.rightArm.y = 4F;
		var2.rightArm.z = 1.5F;
		var2.rightArm.xRot = (float)(Math.toRadians((double)(var8)));
		var2.rightArm.yRot = (float)(Math.toRadians(20D));
		var2.rightArm.zRot = (float)(Math.toRadians(40D));
		ModelPart var9 = var2.leftArm;
		var9.x = (float)(((double)(var9.x)) - (5D));
		var2.leftArm.y = 2F;
		ModelPart var10 = var2.leftArm;
		var10.z = (float)(((double)(var10.z)) - (3D));
		var2.leftArm.xRot = (float)(Math.toRadians((double)((var8) + (15F))));
		var2.leftArm.yRot = (float)(Math.toRadians(20D));
		var2.leftArm.zRot = (float)(Math.toRadians(50D));
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		float var8 = (-95F) + (((float)(this.getTime())) * (20F));
		var8 = Mth.clamp(var8,-100F,-45F);
		var5.translate(0D,0D,0D);
		var5.mulPose(Axis.ZP.rotationDegrees(var8));
		var5.mulPose(Axis.XP.rotationDegrees(-35F));
	}
}