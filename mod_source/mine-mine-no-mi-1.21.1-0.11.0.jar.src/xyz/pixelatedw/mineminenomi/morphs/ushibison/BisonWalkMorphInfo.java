package xyz.pixelatedw.mineminenomi.morphs.ushibison;

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
import xyz.pixelatedw.mineminenomi.abilities.ushibison.BisonWalkPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class BisonWalkMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(1.1F,1.7F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(1.1F,1.6F);
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/bison_walk.png");

	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		float var5 = 1.5F;
		var3.scale(var5,var5,var5);
	}
	public Component getDisplayName() {
		return ((AbilityCore)(BisonWalkPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	public float getEyeHeight(LivingEntity var1) {
		if (var1.isCrouching()){
			return 1.2F;
		}else{
			return 1.5F;
		}
	}
	public float getShadowSize() {
		return 0.9F;
	}
	public boolean canMount() {
		return false;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}