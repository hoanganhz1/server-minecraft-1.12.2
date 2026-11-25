package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractGorillaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.GorillaModel;

public class GorillaRenderer<T extends AbstractGorillaEntity, M extends GorillaModel<T>> extends MobRenderer<T, M> {
	// Fields
	public static final ResourceLocation BLUGORI_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/blugori.png");
	public static final ResourceLocation BLAGORI_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/blagori.png");
	 ResourceLocation texture;

	public GorillaRenderer(EntityRendererProvider.Context var1, Supplier<M> var2, ResourceLocation var3) {
		super(var1,(EntityModel)(((GorillaModel)(var2.get()))),0.8F);
		this.texture = var3;
		this.addLayer((RenderLayer)(new ItemInHandLayer((RenderLayerParent)(this),var1.getItemInHandRenderer())));
	}
	public void scale(T var1, PoseStack var2, float var3) {
		var2.scale(var1.getSize(),var1.getSize(),var1.getSize());
		this.shadowRadius = (var1.getSize()) / (1.9F);
	}
	public ResourceLocation getTextureLocation(T var1) {
		return this.texture;
	}
}