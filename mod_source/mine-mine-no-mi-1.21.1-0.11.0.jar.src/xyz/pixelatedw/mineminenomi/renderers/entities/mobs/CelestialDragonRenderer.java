package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;
import xyz.pixelatedw.mineminenomi.renderers.layers.CelestialDragonSlaveRideLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.hairs.CelestialDragonWigLayer;

public class CelestialDragonRenderer extends OPHumanoidRenderer<CelestialDragonEntity, HumanoidModel<CelestialDragonEntity>> {
	public CelestialDragonRenderer(EntityRendererProvider.Context var1) {
		super(var1,() -> {
			return new HumanoidModel(var1.bakeLayer(ModelLayers.ZOMBIE));
		},new float[]{1F,1F,1F});
		this.addLayer((RenderLayer)(new CelestialDragonWigLayer(var1,(RenderLayerParent)(this))));
		this.addLayer((RenderLayer)(new CelestialDragonSlaveRideLayer(var1,(RenderLayerParent)(this))));
	}
	public void render(CelestialDragonEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		super.render((LivingEntity)(var1),var2,var3,var4,var5,var6);
	}
	protected void setupRotations(CelestialDragonEntity var1, PoseStack var2, float var3, float var4, float var5, float var6) {
		HumanoidModel var7 = ((HumanoidModel)(this.model));
		var7.riding = var1.isRidingSlave();
		super.setupRotations((LivingEntity)(var1),var2,var3,var4,var5,var6);
	}
}