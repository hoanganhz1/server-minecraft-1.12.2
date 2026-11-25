package xyz.pixelatedw.mineminenomi.animations.sniper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class AimSniperAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public AimSniperAnimation(AnimationId<AimSniperAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if ((((!(var1.getMainHandItem().isEmpty())) && ((var1.getMainHandItem().getItem()) == (ModWeapons.FLINTLOCK.get()))) ? (1) : (0)) != (0)){
			HumanoidArm var8 = ((var1.getItemBySlot(EquipmentSlot.MAINHAND).getItem()) == (ModWeapons.FLINTLOCK.get())) ? (HumanoidArm.RIGHT) : (HumanoidArm.LEFT);
			HumanoidArm var9 = ((var1.getItemBySlot(EquipmentSlot.MAINHAND).getItem()) == (ModWeapons.FLINTLOCK.get())) ? (HumanoidArm.LEFT) : (HumanoidArm.RIGHT);
			this.getArm(var2,var8).yRot = ((-0.1F) + (var2.head.yRot)) - (0.4F);
			this.getArm(var2,var9).yRot = (0.5F) + (var2.head.yRot);
			this.getArm(var2,var8).xRot = (-1.5707964F) + (var2.head.xRot);
			this.getArm(var2,var9).xRot = (-1.5707964F) + (var2.head.xRot);
		}else{
			var2.rightArm.yRot = ((0.3F) + (var2.head.yRot)) - (0.4F);
			var2.leftArm.yRot = (0.8F) + (var2.head.yRot);
			var2.rightArm.xRot = (-1.5707964F) + (var2.head.xRot);
			var2.leftArm.xRot = (-1.5707964F) + (var2.head.xRot);
		}
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		if ((((!(var1.getMainHandItem().isEmpty())) && ((var1.getMainHandItem().getItem()) == (ModWeapons.FLINTLOCK.get()))) ? (1) : (0)) != (0)){
			var5.translate(-0.05D,0D,-0.15D);
			var5.mulPose(Axis.YN.rotationDegrees(20F));
		}
	}
	protected ModelPart getArm(HumanoidModel<LivingEntity> var1, HumanoidArm var2) {
		return ((var2) == (HumanoidArm.LEFT)) ? (var1.leftArm) : (var1.rightArm);
	}
}