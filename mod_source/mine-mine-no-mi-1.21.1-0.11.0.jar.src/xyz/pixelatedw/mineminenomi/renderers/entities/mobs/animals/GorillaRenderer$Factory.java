package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import java.util.function.Supplier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractGorillaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.GorillaModel;

public class GorillaRenderer$Factory implements EntityRendererProvider<AbstractGorillaEntity> {
	// Fields
	protected Supplier<GorillaModel<AbstractGorillaEntity>> model;
	 ResourceLocation texture;

	public GorillaRenderer$Factory(EntityRendererProvider.Context var0, ResourceLocation var1) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public EntityRenderer<AbstractGorillaEntity> create(EntityRendererProvider.Context var1) {
		return new GorillaRenderer(var1,this.model,this.texture);
	}
}