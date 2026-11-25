package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
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

public class CrossedAttackAnimation extends Animation<LivingEntity, AgeableListModel<LivingEntity>> {
	public CrossedAttackAnimation(AnimationId<CrossedAttackAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
		this.setAnimationHeldItem((Animation$IAnimationHeldItem)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationHeldItem)((Animation$IAnimationHeldItem) ((Animation$IAnimationHeldItem<LivingEntity>) (this::heldItem))))));
	}
	public void angles(LivingEntity var1, AgeableListModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		ModelPart var8 = null;
		ModelPart var9 = null;
		if (var2 instanceof HumanoidModel){
			HumanoidModel var10 = ((HumanoidModel)(var2));
			var8 = var10.rightArm;
			var9 = var10.leftArm;
		}
		float var10_1 = Mth.lerp(((float)(this.getTime())) / (4F),-180F,-40F);
		var10_1 = Mth.clamp(var10_1,-180F,-40F);
		if ((var8) != (null)){
			var8.xRot = (float)(Math.toRadians((double)(var10_1)));
			var8.yRot = (float)(Math.toRadians(0D));
			var8.zRot = (float)(Math.toRadians(-45D));
		}
		if ((var9) != (null)){
			var9.xRot = (float)(Math.toRadians((double)(var10_1)));
			var9.yRot = (float)(Math.toRadians(0D));
			var9.zRot = (float)(Math.toRadians(45D));
		}
	}
	public void heldItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7) {
		if ((!(var2.isEmpty())) && ((var2.getItem()) == (ModWeapons.CAT_CLAWS.get()))){
			var5.translate(-0.11D,-0.11D,0D);
			var5.mulPose(Axis.ZP.rotationDegrees(90F));
		}
	}
}