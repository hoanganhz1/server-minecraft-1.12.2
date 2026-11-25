package xyz.pixelatedw.mineminenomi.renderers.morphs;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class PlayerMorphRenderer<T extends LivingEntity, M extends HumanoidModel<T>> extends MorphRenderer<T, M> {
	public PlayerMorphRenderer(EntityRendererProvider.Context var1, MorphInfo var2, M var3, boolean var4) {
		super(var1,var2,var3);
		HumanoidArmorModel var5 = new HumanoidArmorModel(var1.bakeLayer((var4) ? (ModelLayers.PLAYER_SLIM_INNER_ARMOR) : (ModelLayers.PLAYER_INNER_ARMOR)));
		HumanoidArmorModel var6 = new HumanoidArmorModel(var1.bakeLayer((var4) ? (ModelLayers.PLAYER_SLIM_OUTER_ARMOR) : (ModelLayers.PLAYER_OUTER_ARMOR)));
		this.addLayer((RenderLayer)(new HumanoidArmorLayer((RenderLayerParent)(this),(HumanoidModel)(var5),(HumanoidModel)(var6),var1.getModelManager())));
	}
}