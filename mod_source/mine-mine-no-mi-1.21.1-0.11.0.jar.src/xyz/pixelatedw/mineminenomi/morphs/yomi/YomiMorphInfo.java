package xyz.pixelatedw.mineminenomi.morphs.yomi;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class YomiMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(0.6F,1.8F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(0.6F,1.5F);
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/yomi_skeleton.png");

	@OnlyIn(value=Dist.CLIENT)
	public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context var1, LivingEntityRenderer<T, M> var2) {
		if (var2.getModel() instanceof HumanoidModel){
			this.addArmorLayer((EntityRendererProvider.Context)(var1),(LivingEntityRenderer)(var2));
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	private <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> void addArmorLayer(EntityRendererProvider.Context var1, LivingEntityRenderer<T, M> var2) {
		HumanoidArmorModel var3 = new HumanoidArmorModel(var1.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR));
		HumanoidArmorModel var4 = new HumanoidArmorModel(var1.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR));
		var2.addLayer((RenderLayer)(new HumanoidArmorLayer(var2,(HumanoidModel)(var3),(HumanoidModel)(var4),var1.getModelManager())));
	}
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.scale(0.98F,0.98F,0.98F);
	}
	public Component getDisplayName() {
		return ModI18n.MORPH_YOMI_NAME;
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	public float getEyeHeight(LivingEntity var1) {
		return 1.74F;
	}
	public float getShadowSize() {
		return 0.5F;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}