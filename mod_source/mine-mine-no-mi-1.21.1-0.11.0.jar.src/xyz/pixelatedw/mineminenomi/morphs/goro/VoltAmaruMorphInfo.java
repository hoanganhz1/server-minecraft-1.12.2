package xyz.pixelatedw.mineminenomi.morphs.goro;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.goro.VoltAmaruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class VoltAmaruMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(2.4F,6.5F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(2.4F,6.2F);

	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.scale(2.5F,2.5F,2.5F);
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		if (var1 instanceof AbstractClientPlayer){
			return ((AbstractClientPlayer)(var1)).getSkin().texture();
		}else{
			return null;
		}
	}
	public Component getDisplayName() {
		return ((AbilityCore)(VoltAmaruAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 5.5F;
	}
	public float getShadowSize() {
		return 1.9F;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraZoom(LivingEntity var1) {
		return 10D;
	}
	public boolean canMount() {
		return false;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}