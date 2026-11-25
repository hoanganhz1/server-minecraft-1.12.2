package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoDawnWhipAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.effects.SpiralEffectModel;

public class GomuDawnWhipLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private SpiralEffectModel<T> model;

	public GomuDawnWhipLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new SpiralEffectModel(var1.bakeLayer(SpiralEffectModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		Function<AbilityData, GomuGomuNoDawnWhipAbility> var11 = (l0) -> {
			return ((GomuGomuNoDawnWhipAbility)(l0.getEquippedAbility(((AbilityCore)(GomuGomuNoDawnWhipAbility.INSTANCE.get())))));
		};
		Function<GomuGomuNoDawnWhipAbility, Boolean> var12 = (l0) -> {
			return Boolean.valueOf(l0.isContinuous());
		};
		if (!(((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var4))).map((Function)(var11)))).map((Function)(var12)).orElse(Boolean.valueOf(false)))).booleanValue())){
			return;
		}else{
			var1.pushPose();
			var1.translate(0D,1.55D,0.25D);
			var1.mulPose(Axis.XP.rotationDegrees(90F));
			float var13 = 2.5F;
			var1.scale(var13,var13,var13);
			VertexConsumer var14 = var2.getBuffer(RenderType.entityTranslucent(ModResources.PROJ_EFFECT_2));
			this.model.setupAnim(var4,0F,0F,(var8) * (1.2F),0F,0F);
			this.model.renderToBuffer(var1,var14,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,1F,1F,0.5F));
			var1.popPose();
			var1.pushPose();
			var1.mulPose(Axis.YP.rotationDegrees(45F));
			VertexConsumer var15 = var2.getBuffer(ModRenderTypes.ENERGY);
			this.getParentModel().setupAnim(var4,var5,var6,var8,var9,var10);
			this.getParentModel().renderToBuffer(var1,var15,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,1F,1F,0.1F));
			var1.mulPose(Axis.YP.rotationDegrees(90F));
			this.getParentModel().setupAnim(var4,var5,var6,var8,var9,var10);
			this.getParentModel().renderToBuffer(var1,var15,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(1F,1F,1F,0.1F));
			var1.popPose();
			return;
		}
	}
}