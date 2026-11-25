package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import java.util.function.Supplier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class DugongRenderer$Factory implements EntityRendererProvider<AbstractDugongEntity> {
	// Fields
	protected Supplier<DugongModel<AbstractDugongEntity>> model;
	final ResourceLocation texture;

	public DugongRenderer$Factory(EntityRendererProvider.Context var0, ResourceLocation var1) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public EntityRenderer<AbstractDugongEntity> create(EntityRendererProvider.Context var1) {
		return new DugongRenderer(var1,this.model,this.texture);
	}
}