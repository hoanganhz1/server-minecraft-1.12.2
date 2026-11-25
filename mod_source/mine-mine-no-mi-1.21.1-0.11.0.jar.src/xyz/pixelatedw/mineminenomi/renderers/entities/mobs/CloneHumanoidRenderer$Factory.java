package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.CloneEntity;

public class CloneHumanoidRenderer$Factory implements EntityRendererProvider<CloneEntity> {
	// Fields
	 ResourceLocation texture;
	 boolean useSimpleModel;

	public CloneHumanoidRenderer$Factory setTexture(ResourceLocation var1) {
		this.texture = var1;
		return this;
	}
	public CloneHumanoidRenderer$Factory setSimpleModel() {
		this.useSimpleModel = true;
		return this;
	}
	public EntityRenderer<CloneEntity> create(EntityRendererProvider.Context var1) {
		CloneHumanoidRenderer var2 = new CloneHumanoidRenderer(var1);
		var2.setTexture(this.texture);
		if (this.useSimpleModel){
			var2.setSimpleModel();
		}
		return var2;
	}
}