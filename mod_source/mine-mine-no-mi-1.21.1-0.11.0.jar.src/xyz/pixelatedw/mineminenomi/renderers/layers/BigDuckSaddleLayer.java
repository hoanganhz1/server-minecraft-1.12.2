package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BigDuckEntity;

public class BigDuckSaddleLayer<T extends BigDuckEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final ResourceLocation SADDLE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/big_duck_saddle.png");

	public BigDuckSaddleLayer(RenderLayerParent<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if ((var4.isTame()) && (var4.isSaddled())){
			var1.pushPose();
			var1.scale(1.1F,1.1F,1.1F);
			var1.translate(0D,-0.01D,0D);
			RenderLayer.renderColoredCutoutModel(this.getParentModel(),SADDLE_TEXTURE,var1,var2,var3,var4,WyHelper.packColor(1F,1F,1F,1F));
			var1.popPose();
		}
	}
}