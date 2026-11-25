package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.CloneHumanoidRenderer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.NightmareSoldierLayer;

public class NightmareSoldierRenderer extends CloneHumanoidRenderer<NightmareSoldierEntity, HumanoidModel<NightmareSoldierEntity>> {
	public NightmareSoldierRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.setSimpleModel();
		this.addLayer((RenderLayer)(new NightmareSoldierLayer((RenderLayerParent)(this))));
	}
}