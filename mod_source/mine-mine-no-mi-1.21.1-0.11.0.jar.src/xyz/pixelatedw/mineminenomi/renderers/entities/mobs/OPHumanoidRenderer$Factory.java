package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import java.util.function.Supplier;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class OPHumanoidRenderer$Factory<M extends Mob> implements EntityRendererProvider<M> {
	// Fields
	private EntityRendererProvider.Context ctx;
	protected Supplier<HumanoidModel<M>> model;
	 float[] scale;
	 ResourceLocation texture;

	public OPHumanoidRenderer$Factory(EntityRendererProvider.Context var1) {
		this(var1,1F);
	}
	public OPHumanoidRenderer$Factory(EntityRendererProvider.Context var1, float var2) {
		this(var1,var2,var2,var2);
	}
	public OPHumanoidRenderer$Factory(EntityRendererProvider.Context var0, float var1, float var2, float var3) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public OPHumanoidRenderer$Factory<M> setModelLayer(ModelLayerLocation var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public OPHumanoidRenderer$Factory<M> setTexture(ResourceLocation var1) {
		this.texture = var1;
		return this;
	}
	public EntityRenderer<M> create(EntityRendererProvider.Context var1) {
		OPHumanoidRenderer var2 = new OPHumanoidRenderer(var1,this.model,this.scale);
		var2.setTexture(this.texture);
		return var2;
	}
}