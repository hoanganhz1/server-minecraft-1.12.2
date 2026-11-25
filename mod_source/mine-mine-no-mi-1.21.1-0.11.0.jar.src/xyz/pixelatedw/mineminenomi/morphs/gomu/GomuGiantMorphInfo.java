package xyz.pixelatedw.mineminenomi.morphs.gomu;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.deka.DekaDekaAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GomuGiantMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(4F,8.5F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(4F,7.6F);

	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.scale(2.1F,2.1F,2.1F);
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		if (var1 instanceof AbstractClientPlayer){
			return ((AbstractClientPlayer)(var1)).getSkin().texture();
		}else{
			return null;
		}
	}
	public Component getDisplayName() {
		return ((AbilityCore)(DekaDekaAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 8.45F;
	}
	public float getShadowSize() {
		return 1.25F;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraZoom(LivingEntity var1) {
		return 8D;
	}
	public boolean canMount() {
		return false;
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
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}