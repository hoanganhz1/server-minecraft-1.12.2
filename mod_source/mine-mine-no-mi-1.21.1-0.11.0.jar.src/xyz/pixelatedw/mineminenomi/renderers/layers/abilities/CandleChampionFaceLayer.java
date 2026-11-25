package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class CandleChampionFaceLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel & HeadedModel> extends RenderLayer<T, M> {
	// Fields
	private MorphRenderer<T, M> parent;

	public CandleChampionFaceLayer(MorphRenderer<T, M> var1) {
		super(var1);
		this.parent = var1;
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if (var4.isInvisible()){
			return;
		}else{
			var1.pushPose();
			var1.translate(0D,0D,-0.2D);
			var1.scale(0.5F,0.5F,0.5F);
			var10 = Mth.clamp(var10,-17F,60F);
			var9 = Mth.clamp(var9,-27F,27F);
			if ((var4.attackAnim) > (0F)){
				var9 = (float)(Math.toDegrees((double)((Mth.sin((Mth.sqrt(var4.attackAnim)) * (6.2831855F))) * (0.2F))));
			}
			LivingEntityRenderer var11 = ((LivingEntityRenderer)(this.parent.getOriginalRenderer()));
			RenderType var12 = RenderType.entityCutout(var11.getTextureLocation(var4));
			Consumer<? super ModelPart> var13 = (l0) -> {
			l0.visible = false;
		};
			((java.lang.Iterable)(RendererHelper.getModelParts(var11.getModel()))).forEach((Consumer)(var13));
			var11.getModel().young = false;
			((HeadedModel)(var11.getModel())).getHead().visible = true;
			var11.getModel().prepareMobModel(var4,var5,var6,var7);
			var11.getModel().setupAnim(var4,var5,var6,var8,var9,var10);
			var11.getModel().renderToBuffer(var1,var2.getBuffer(var12),var3,OverlayTexture.NO_OVERLAY);
			var1.popPose();
			return;
		}
	}
}