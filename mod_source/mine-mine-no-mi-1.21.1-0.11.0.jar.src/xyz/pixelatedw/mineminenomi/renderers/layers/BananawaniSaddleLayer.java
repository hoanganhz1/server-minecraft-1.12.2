package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.BananawaniSaddleModel;

public class BananawaniSaddleLayer<T extends BananawaniEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private final BananawaniSaddleModel<T> model;
	private static final ResourceLocation SADDLE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/bananawani_saddle.png");
	private static final ResourceLocation SADDLE_OVERLAY_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/bananawani_saddle_overlay.png");

	public BananawaniSaddleLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new BananawaniSaddleModel(var1.bakeLayer(BananawaniSaddleModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if ((var4.isTame()) && (var4.isSaddled())){
			int var11 = var4.getSaddleColor().getTextureDiffuseColor();
			RenderLayer.renderColoredCutoutModel((EntityModel)(this.model),SADDLE_OVERLAY_TEXTURE,var1,var2,var3,var4,WyHelper.packColor(1F,1F,1F,1F));
			RenderLayer.renderColoredCutoutModel((EntityModel)(this.model),SADDLE_TEXTURE,var1,var2,var3,var4,var11);
		}
	}
}