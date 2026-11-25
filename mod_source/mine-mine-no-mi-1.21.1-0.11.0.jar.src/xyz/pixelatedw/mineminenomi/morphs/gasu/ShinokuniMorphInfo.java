package xyz.pixelatedw.mineminenomi.morphs.gasu;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.gasu.ShinokuniAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.ShinokuniFaceLayer;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class ShinokuniMorphInfo extends MorphInfo {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/shinokuni.png");
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(2.8F,6F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(2.8F,5.9F);

	@OnlyIn(value=Dist.CLIENT)
	public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context var1, LivingEntityRenderer<T, M> var2) {
		if (var2 instanceof MorphRenderer){
			MorphRenderer var3 = ((MorphRenderer)(var2));
			var2.addLayer((RenderLayer)(new ShinokuniFaceLayer(var3)));
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.scale(3F,3F,3F);
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	public Component getDisplayName() {
		return ((AbilityCore)(ShinokuniAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 5.7F;
	}
	public float getShadowSize() {
		return 1.2F;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraZoom(LivingEntity var1) {
		return 7D;
	}
	public boolean canMount() {
		return false;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}