package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Optional;
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
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruBallAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.SphereModel;

public class DoruBallLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private final SphereModel<T> model;

	public DoruBallLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new SphereModel(var1.bakeLayer(SphereModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		AbilityData var11 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var4))).orElse((Object)(null))));
		if ((var11) == (null)){
			return;
		}else{
			DoruDoruBallAbility var12 = ((DoruDoruBallAbility)(var11.getEquippedAbility(((AbilityCore)(DoruDoruBallAbility.INSTANCE.get())))));
			if (((var12) != (null)) && (var12.isContinuous())){
				var12.rotateAngleX = (var12.rotateAngleX) + ((double)(var4.xxa));
				var12.rotateAngleX = (var12.rotateAngleX) % (360D);
				var12.rotateAngleZ = (var12.rotateAngleZ) + ((double)(var4.zza));
				var12.rotateAngleZ = (var12.rotateAngleZ) % (360D);
				var12.randomizeColor(var4);
				var1.pushPose();
				var1.translate(0D,0.3D,0D);
				float var13 = 8F;
				var1.scale(var13,var13,var13);
				var1.mulPose(Axis.XP.rotationDegrees((float)(var12.rotateAngleZ)));
				var1.mulPose(Axis.ZP.rotationDegrees((float)(var12.rotateAngleX)));
				VertexConsumer var14 = var2.getBuffer(RenderType.entityCutout(ModResources.CANDLE_TEXTURE));
				this.model.renderToBuffer(var1,var14,var3,OverlayTexture.NO_OVERLAY,WyHelper.packColor(var12.color[0],var12.color[1],var12.color[2],1F));
				var1.popPose();
				return;
			}else{
				return;
			}
		}
	}
}