package xyz.pixelatedw.mineminenomi.morphs.toriphoenix;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixFlyPointAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.PhoenixFlyFlamesLayer;

public class PhoenixFlyMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(1.7F,1.7F);
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/phoenix_fly.png");
	private static final ResourceLocation CHICKEN = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/phoenix_chicken.png");

	@Nullable()
	public ResourceLocation getTexture(LivingEntity var1) {
		if (WyHelper.isAprilFirst()){
			return CHICKEN;
		}else{
			return TEXTURE;
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context var1, LivingEntityRenderer<T, M> var2) {
		var2.addLayer((RenderLayer)(new PhoenixFlyFlamesLayer(var1,var2)));
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		float var5 = 0.0F;
		if (WyHelper.isAprilFirst()){
			var5 = 1.8F;
			var3.scale(var5,var5,var5);
			var3.translate(0F,0.35F,0F);
			return;
		}else{
			var5 = 1.7F;
			var3.scale(var5,var5,var5);
			var3.translate(0D,-0.25D,0D);
			return;
		}
	}
	public Component getDisplayName() {
		return ((AbilityCore)(PhoenixFlyPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 0.8F;
	}
	public float getShadowSize() {
		return 1F;
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraZoom(LivingEntity var1) {
		return 8D;
	}
	public boolean canMount() {
		return false;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean hasEqualDepthTest() {
		return true;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,STANDING_SIZE).build();
	}
}