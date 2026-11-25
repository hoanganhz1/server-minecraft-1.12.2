package xyz.pixelatedw.mineminenomi.morphs.ryubrachiosaurus;

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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachiosaurusGuardPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class BrachiosaurusGuardMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(5.5F,10.5F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(5.5F,10.3F);
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/brachiosaurus_guard.png");

	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		float var5 = 3.5F;
		var3.scale(var5,var5,var5);
	}
	public Component getDisplayName() {
		return ((AbilityCore)(BrachiosaurusGuardPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	public float getEyeHeight(LivingEntity var1) {
		return 10F;
	}
	public float getShadowSize() {
		return 3.5F;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraZoom(LivingEntity var1) {
		return 8D;
	}
	public boolean canMount() {
		return false;
	}
	public void positionRider(LivingEntity var1, Entity var2) {
		double var3 = (var1.getY()) + ((double)((var1.getDimensions(var1.getPose()).height()) * (0.46F)));
		var2.setPos(var1.getX(),var3,var1.getZ());
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}