package xyz.pixelatedw.mineminenomi.morphs.kage;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.kage.ShadowsAsgardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class ShadowsAsgardMorphInfo extends MorphInfo {
	// Fields
	private int shadows;

	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		Function<AbilityData, ShadowsAsgardAbility> var5 = (l0) -> {
			return ((ShadowsAsgardAbility)(l0.getEquippedAbility(((AbilityCore)(ShadowsAsgardAbility.INSTANCE.get())))));
		};
		Function<ShadowsAsgardAbility, Integer> var6 = (l0) -> {
			return Integer.valueOf(l0.getShadows());
		};
		this.shadows = ((Integer)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var5)))).map((Function)(var6)).orElse(Integer.valueOf(0)))).intValue();
		float var7 = (1F) + ((((float)(this.shadows)) * (5F)) / (180F));
		var3.scale(var7,var7,var7);
	}
	public Component getDisplayName() {
		return ((AbilityCore)(ShadowsAsgardAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return (1.8F) + ((((float)(this.shadows)) * (6.7F)) / (180F));
	}
	public float getShadowSize() {
		return (0.5F) + ((((float)(this.shadows)) * (6F)) / (180F));
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraHeight(LivingEntity var1) {
		int var2 = ((Minecraft.getInstance().options.getCameraType()) == (CameraType.FIRST_PERSON)) ? (1) : (0);
		int var3 = ((var1.isPassenger()) && (((var1.getVehicle()) != (null)) && (var1.getVehicle().shouldRiderSit()))) ? (1) : (0);
		if (((var2) != (0)) && ((var3) != (0))){
			return 0.5D;
		}else{
			return 0D;
		}
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,EntityDimensions.scalable((0.6F) + (((float)(this.shadows)) / (30F)),(1.65F) + (((float)(this.shadows)) / (15F)))).put(Pose.CROUCHING,EntityDimensions.scalable((0.6F) + (((float)(this.shadows)) / (30F)),(1.45F) + (((float)(this.shadows)) / (15F)))).build();
	}
}