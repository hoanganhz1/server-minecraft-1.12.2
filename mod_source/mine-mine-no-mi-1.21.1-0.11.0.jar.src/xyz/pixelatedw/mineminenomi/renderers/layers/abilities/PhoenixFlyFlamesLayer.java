package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.models.morphs.PhoenixFlyModel;

public class PhoenixFlyFlamesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final int MAX_FRAMES = 8;
	private static final float FRAME_TIME_MS = 1000F;
	private static final ResourceLocation[] TEXTURES = generateTextureArray();

	private static ResourceLocation[] generateTextureArray() {
		ResourceLocation[] var0 = new ResourceLocation[8];
		int var1 = 0;
		do{
			if ((var1) < (8)){
				int var2 = var1;
				String var3 = "textures/models/morphs/phoenix_flames/fly_" + var2 + ".png";
				var0[var1] = ResourceLocation.fromNamespaceAndPath("mineminenomi",var3);
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var0;
	}
	public PhoenixFlyFlamesLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		EntityModel var11 = this.getParentModel();
		if (var11 instanceof PhoenixFlyModel){
			PhoenixFlyModel var12 = ((PhoenixFlyModel)(var11));
			var12.renderFlames(var1,var2.getBuffer(RenderType.entityTranslucent(this.getFrameTexture())),var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,1F,1F,0.9F));
		}
	}
	private ResourceLocation getFrameTexture() {
		int var1 = Math.round((((float)(Util.getMillis())) % (1000F)) / ((1000F) / ((float)(TEXTURES.length))));
		if ((var1) >= (8)){
			return TEXTURES[0];
		}else{
			return TEXTURES[var1];
		}
	}
}