package xyz.pixelatedw.mineminenomi.morphs.zou;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.zou.ZouGuardPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class ZouGuardMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(1.5F,3F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(1.5F,2.9F);
	private static final EntityDimensions FLYING_SIZE = EntityDimensions.scalable(1.5F,3F);
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/zou_guard.png");

	public ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.scale(1.8F,1.8F,1.8F);
	}
	public Component getDisplayName() {
		return ((AbilityCore)(ZouGuardPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 2.9F;
	}
	public float getShadowSize() {
		return 1.5F;
	}
	public boolean canMount() {
		return false;
	}
	public void positionRider(LivingEntity var1, Entity var2) {
		var2.setPos(var1.getPassengerRidingPosition(var2).add(0D,-0.5D,0D));
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).put(Pose.FALL_FLYING,FLYING_SIZE).build();
	}
}