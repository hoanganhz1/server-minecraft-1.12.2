package xyz.pixelatedw.mineminenomi.morphs.zoumammoth;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.zoumammoth.MammothHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class MammothHeavyMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(3F,4.5F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(3F,4.5F);

	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		Iterator var5 = RendererHelper.getLegPartsFrom(var2.getModel(),HumanoidArm.RIGHT).iterator();
		do{
			if (var5.hasNext()){
				ModelPart var6 = ((ModelPart)(var5.next()));
				var6.visible = false;
				continue;
			}else{
				break;
			}
		} while (true);
		Iterator var6_1 = RendererHelper.getLegPartsFrom(var2.getModel(),HumanoidArm.LEFT).iterator();
		do{
			if (var6_1.hasNext()){
				ModelPart var7 = ((ModelPart)(var6_1.next()));
				var7.visible = false;
				continue;
			}else{
				break;
			}
		} while (true);
		float var7_1 = 2F;
		var3.scale(var7_1,var7_1,var7_1);
	}
	@OnlyIn(value=Dist.CLIENT)
	public void postRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		EntityModel var5 = var2.getModel();
		if (var5 instanceof HumanoidModel){
			HumanoidModel var6 = ((HumanoidModel)(var5));
			var6.head.z = -18F;
			var6.head.y = -8F;
			var6.body.z = -17F;
			var6.body.y = -9F;
			var6.body.xRot = 0.25F;
			var6.body.xScale = 1.74F;
			var6.body.zScale = 2F;
			ModelPart var7 = var6.rightArm;
			var7.xRot = (var7.xRot) / (5F);
			var6.rightArm.x = -8F;
			var6.rightArm.y = -6.5F;
			var6.rightArm.z = -17.5F;
			var6.rightArm.yScale = 1.2F;
			ModelPart var8 = var6.leftArm;
			var8.xRot = (var8.xRot) / (5F);
			var6.leftArm.x = 8F;
			var6.leftArm.y = -6.5F;
			var6.leftArm.z = -17.5F;
			var6.leftArm.yScale = 1.2F;
			if (var6 instanceof PlayerModel){
				PlayerModel var9 = ((PlayerModel)(var6));
				var9.leftSleeve.copyFrom(var9.leftArm);
				var9.rightSleeve.copyFrom(var9.rightArm);
			}else{
				return;
			}
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void armorVisibilityCallback(LivingEntity var1, Model var2, EquipmentSlot var3) {
		if (var2 instanceof HumanoidModel){
			HumanoidModel var4 = ((HumanoidModel)(var2));
			if (((var3) != (EquipmentSlot.LEGS)) && ((var3) != (EquipmentSlot.FEET))){
				return;
			}else{
				var4.rightLeg.visible = false;
				var4.leftLeg.visible = false;
			}
		}
	}
	public Component getDisplayName() {
		return ((AbilityCore)(MammothHeavyPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		return MammothGuardMorphInfo.TEXTURE;
	}
	public float getEyeHeight(LivingEntity var1) {
		return 4.2F;
	}
	public float getShadowSize() {
		return 2F;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraZoom(LivingEntity var1) {
		return 6D;
	}
	public boolean canMount() {
		return false;
	}
	public void positionRider(LivingEntity var1, Entity var2) {
		double var3 = (var1.getY()) + (((double)(var1.getDimensions(var1.getPose()).height())) * (0.5D));
		var2.setPos(var1.getX(),var3,var1.getZ());
	}
	public boolean isPartial() {
		return true;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}