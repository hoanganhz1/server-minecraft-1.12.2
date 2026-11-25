package xyz.pixelatedw.mineminenomi.morphs.ryupteranodon;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.PteranodonFlyPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class PteranodonFlyMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(1.8F,1.8F);
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/pteranodon_fly.png");

	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		float var5 = 2.5F;
		var3.scale(var5,var5,var5);
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	public Component getDisplayName() {
		return ((AbilityCore)(PteranodonFlyPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 0.8F;
	}
	public float getShadowSize() {
		return 2F;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraZoom(LivingEntity var1) {
		return 8D;
	}
	public boolean canMount() {
		return false;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,STANDING_SIZE).build();
	}
}