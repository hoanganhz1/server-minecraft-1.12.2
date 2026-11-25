package xyz.pixelatedw.mineminenomi.morphs.doru;

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
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleChampionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.CandleChampionFaceLayer;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class CandleChampionMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(2.8F,4F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(2.8F,3.9F);

	@OnlyIn(value=Dist.CLIENT)
	public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context var1, LivingEntityRenderer<T, M> var2) {
		if (var2 instanceof MorphRenderer){
			MorphRenderer var3 = ((MorphRenderer)(var2));
			var2.addLayer((RenderLayer)(new CandleChampionFaceLayer(var3)));
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.scale(2F,2F,2F);
		var3.translate(0D,-0D,0D);
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		return ModResources.CANDLE_TEXTURE;
	}
	public Component getDisplayName() {
		return ((AbilityCore)(CandleChampionAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 3.4F;
	}
	public float getShadowSize() {
		return 1.2F;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}