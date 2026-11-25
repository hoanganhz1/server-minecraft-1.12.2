package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.Util;
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
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFifthAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.models.entities.AwakeningSmokeModel;

public class AwakeningSmokeLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final ResourceLocation SMOKE_0 = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/awakening_smoke/smoke_0.png");
	private static final ResourceLocation SMOKE_1 = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/awakening_smoke/smoke_1.png");
	private static final ResourceLocation SMOKE_2 = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/awakening_smoke/smoke_2.png");
	private static final ResourceLocation SMOKE_3 = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/awakening_smoke/smoke_3.png");
	private static final ResourceLocation[] SMOKE_ANIM;
	private static final float SCALE = 1.3F;
	private AwakeningSmokeModel model;

	public AwakeningSmokeLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.model = new AwakeningSmokeModel(var1.bakeLayer(AwakeningSmokeModel.LAYER_LOCATION));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if (!(this.canRender(var4))){
			return;
		}else{
			float var11 = 1000F;
			float var12 = (((float)(Util.getMillis())) % (var11)) / ((var11) / ((float)(SMOKE_ANIM.length)));
			var1.pushPose();
			var1.scale(1.3F,1.3F,1.3F);
			VertexConsumer var13 = var2.getBuffer(RenderType.entityTranslucent(SMOKE_ANIM[(int)(Math.floor((double)(var12)))]));
			this.model.setupAnim(var4,0F,0F,0F,0F,0F);
			this.model.renderToBuffer(var1,var13,var3,OverlayTexture.NO_OVERLAY);
			var1.popPose();
			return;
		}
	}
	public boolean canRender(T var1) {
		Function<AbilityData, GearFifthAbility> var2 = (l0) -> {
			return ((GearFifthAbility)(l0.getEquippedAbility(((AbilityCore)(GearFifthAbility.INSTANCE.get())))));
		};
		return ((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var2)).map((Function)(((Function<GearFifthAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	static  {
		ResourceLocation[] var0 = new ResourceLocation[]{SMOKE_0,SMOKE_1,SMOKE_2,SMOKE_3};
		SMOKE_ANIM = var0;
	}
}