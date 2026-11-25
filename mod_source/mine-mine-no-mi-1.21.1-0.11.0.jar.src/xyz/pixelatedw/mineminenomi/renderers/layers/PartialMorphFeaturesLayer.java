package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.extensions.IAgeableListModelExtension;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModMorphs$Client;

public class PartialMorphFeaturesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public PartialMorphFeaturesLayer(LivingEntityRenderer<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		DevilFruitData var11 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var4))).orElse((Object)(null))));
		if ((var11) == (null)){
			return;
		}else{
			if (var4.hasEffect(MobEffects.INVISIBILITY)){
				return;
			}else{
				boolean var12 = false;
				Iterator var13 = var11.getActiveMorphs().iterator();
				do{
					if (var13.hasNext()){
						MorphInfo var14 = ((MorphInfo)(var13.next()));
						if ((var14.isPartial()) && (var14.canRender(var4))){
							EntityModel var15 = ((EntityModel)(ModMorphs$Client.PARTIAL_MORPHS_RENDERERS.get(var14)));
							ResourceLocation var16 = var14.getTexture(var4);
							if (((var15) != (null)) && ((var16) != (null))){
								this.getParentModel().copyPropertiesTo(var15);
								if (var15 instanceof HumanoidModel){
									HumanoidModel var17 = ((HumanoidModel)(var15));
									EntityModel var18 = this.getParentModel();
									if (var18 instanceof HumanoidModel){
										((HumanoidModel)(var18)).copyPropertiesTo(var17);
									}
								}
								var15.prepareMobModel(var4,var5,var6,var7);
								var15.setupAnim(var4,var5,var6,var8,var9,var10);
								var15.renderToBuffer(var1,var2.getBuffer(RenderType.entityTranslucent(var16)),var3,LivingEntityRenderer.getOverlayCoords(var4,0F));
							}
							var12 = true;
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if (var12){
					((IAgeableListModelExtension)(this.getParentModel())).getParts().forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
				}
				return;
			}
		}
	}
}