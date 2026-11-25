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

public class OneTwoJangoAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public OneTwoJangoAnimation(AnimationId<OneTwoJangoAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		ModelPart var8 = var2.rightArm;
		var8.xRot = (((var8.xRot) * (0.5F)) - (3.1415927F)) + (1.5F);
		var8.yRot = 0.2F;
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		if ((var1.getMainArm()) == (var4)){
			float var8 = ((float)(Math.toDegrees(Math.sin(((double)((0.6F) * ((float)(var1.tickCount)))) + (3.141592653589793D))))) * (0.15F);
			var5.mulPose(Axis.ZP.rotationDegrees(var8));
			var5.translate(0.1D,-0.4D,0.05D);
			var5.mulPose(Axis.XP.rotationDegrees(-110F));
			var5.mulPose(Axis.YP.rotationDegrees(5F));
			var5.mulPose(Axis.ZP.rotationDegrees(-90F));
		}
	}
}