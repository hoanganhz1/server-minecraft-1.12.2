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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.abilities.mini.MiniMiniAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.CubeModel;

public class PaperFloatLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private final CubeModel<T> model;

	public PaperFloatLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new CubeModel(var1.bakeLayer(CubeModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		int var11 = (((var4.getMainHandItem().getItem()) == (Items.PAPER)) || ((var4.getOffhandItem().getItem()) == (Items.PAPER))) ? (1) : (0);
		int var12 = ((!(var4.onGround())) && ((var4.getDeltaMovement().y) < (0D))) ? (1) : (0);
		if (((var11) != (0)) && ((var12) != (0))){
			Function<AbilityData, MiniMiniAbility> var13 = (l0) -> {
			return ((MiniMiniAbility)(l0.getEquippedAbility(((AbilityCore)(MiniMiniAbility.INSTANCE.get())))));
		};
			Function<MiniMiniAbility, Boolean> var14 = (l0) -> {
			return Boolean.valueOf(l0.isContinuous());
		};
			if (((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var4))).map((Function)(var13)))).map((Function)(var14)).orElse(Boolean.valueOf(false)))).booleanValue()){
				var1.pushPose();
				var1.translate(0D,-0.7D,0D);
				var1.mulPose(Axis.YP.rotationDegrees(var9));
				var1.scale(2.4F,0.5F,2.5F);
				VertexConsumer var15 = var2.getBuffer(ModRenderTypes.TRANSPARENT_COLOR);
				this.model.renderToBuffer(var1,var15,var3,OverlayTexture.NO_OVERLAY);
				var1.popPose();
			}
			return;
		}else{
			return;
		}
	}
}