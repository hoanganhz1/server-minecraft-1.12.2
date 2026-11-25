package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import java.util.function.Supplier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WanderingDugongEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class WanderingDugongRenderer$Factory implements EntityRendererProvider<WanderingDugongEntity> {
	// Fields
	protected Supplier<DugongModel<WanderingDugongEntity>> model;

	public WanderingDugongRenderer$Factory(EntityRendererProvider.Context var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public EntityRenderer<WanderingDugongEntity> create(EntityRendererProvider.Context var1) {
		return new WanderingDugongRenderer(var1,this.model);
	}
}