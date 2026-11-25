package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WanderingDugongEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class WanderingDugongScarsLayer<T extends WanderingDugongEntity, M extends DugongModel<T>> extends RenderLayer<T, M> {
	public WanderingDugongScarsLayer(RenderLayerParent<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if ((var4.getHeadScarId()) >= (0)){
			ResourceLocation var11 = MobsHelper.DUGONG_HEAD_SCARS_TEXTURES[var4.getHeadScarId()];
			VertexConsumer var12 = var2.getBuffer(RenderType.entityCutoutNoCull(var11));
			((DugongModel)(this.getParentModel())).renderToBuffer(var1,var12,var3,OverlayTexture.NO_OVERLAY);
		}
		if ((var4.getChestScarId()) >= (0)){
			ResourceLocation var11_1 = MobsHelper.DUGONG_CHEST_SCARS_TEXTURES[var4.getChestScarId()];
			VertexConsumer var12_1 = var2.getBuffer(RenderType.entityCutoutNoCull(var11_1));
			((DugongModel)(this.getParentModel())).renderToBuffer(var1,var12_1,var3,OverlayTexture.NO_OVERLAY);
		}
		if ((var4.getArmsScarId()) >= (0)){
			ResourceLocation var11_2 = MobsHelper.DUGONG_ARMS_SCARS_TEXTURES[var4.getArmsScarId()];
			VertexConsumer var12_2 = var2.getBuffer(RenderType.entityCutoutNoCull(var11_2));
			((DugongModel)(this.getParentModel())).renderToBuffer(var1,var12_2,var3,OverlayTexture.NO_OVERLAY);
		}
		if ((var4.getTailScarId()) >= (0)){
			ResourceLocation var11_3 = MobsHelper.DUGONG_TAIL_SCARS_TEXTURES[var4.getTailScarId()];
			VertexConsumer var12_3 = var2.getBuffer(RenderType.entityCutoutNoCull(var11_3));
			((DugongModel)(this.getParentModel())).renderToBuffer(var1,var12_3,var3,OverlayTexture.NO_OVERLAY);
		}
	}
}