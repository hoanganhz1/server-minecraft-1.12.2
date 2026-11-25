package xyz.pixelatedw.mineminenomi.animations.mandemontactics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DemonicSmashAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public DemonicSmashAnimation(AnimationId<DemonicSmashAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		int var8 = ((!(var1.getMainHandItem().isEmpty())) && ((var1.getMainHandItem().getItem()) == (ModWeapons.TONFA.get()))) ? (1) : (0);
		int var9 = ((!(var1.getOffhandItem().isEmpty())) && ((var1.getOffhandItem().getItem()) == (ModWeapons.TONFA.get()))) ? (1) : (0);
		double var10 = (double)((180L) - ((this.getTime()) * (40L)));
		var10 = Math.max(var10,20D);
		if ((var8) != (0)){
			var2.rightArm.xRot = (float)(Math.toRadians(var10));
			var2.rightArm.zRot = (float)(Math.toRadians(-90D));
			var2.rightArm.yRot = (float)(Math.toRadians(-180D));
		}
		if ((var9) != (0)){
			var2.leftArm.xRot = (float)(Math.toRadians((20D) + (var10)));
			var2.leftArm.zRot = (float)(Math.toRadians(-90D));
			var2.leftArm.yRot = (float)(Math.toRadians(-180D));
		}
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		if ((var2.getItem()) != (ModWeapons.TONFA.get())){
			return;
		}else{
			var5.translate(0D,0D,0.2D);
			var5.mulPose(Axis.XP.rotationDegrees(10F));
			var5.mulPose(Axis.YP.rotationDegrees(180F));
			return;
		}
	}
}