package xyz.pixelatedw.mineminenomi.renderers.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.PhysicalBodyEntity;

public class PhysicalBodyRenderer$Factory implements EntityRendererProvider<PhysicalBodyEntity> {
	// Fields
	private boolean isSpooky;

	public PhysicalBodyRenderer$Factory setSpooky() {
		this.isSpooky = true;
		return this;
	}
	public EntityRenderer<PhysicalBodyEntity> create(EntityRendererProvider.Context var1) {
		PhysicalBodyRenderer var2 = new PhysicalBodyRenderer(var1);
		if (this.isSpooky){
			var2.setSpooky(var1);
		}
		return var2;
	}
}