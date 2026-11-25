package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class NightmareSoldierLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public NightmareSoldierLayer(RenderLayerParent<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		EntityModel var11 = this.getParentModel();
		if (var11 instanceof HumanoidModel){
			HumanoidModel var12 = ((HumanoidModel)(var11));
			var12.hat.visible = false;
		}
		VertexConsumer var12_1 = var2.getBuffer(ModRenderTypes.getAbilityBody(ModResources.BUSOSHOKU_HAKI_ARM));
		this.getParentModel().renderToBuffer(var1,var12_1,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(0.2F,0.2F,0.2F,0.8F));
	}
}