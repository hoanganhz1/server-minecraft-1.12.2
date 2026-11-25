package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.morph.RacialRenderFeature;
import xyz.pixelatedw.mineminenomi.api.morph.RacialRenderFeature$ILayerRenderExtension;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModMorphs$Client;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class RacialFeaturesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	// Fields
	private RenderLayerParent<T, M> parentRenderer;

	public RacialFeaturesLayer(RenderLayerParent<T, M> var1) {
		super(var1);
		this.parentRenderer = var1;
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		EntityStatsData var11 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var4))).orElse((Object)(null))));
		if ((var11) == (null)){
			return;
		}else{
			Class[] var12 = new Class[]{PlayerModel.class,HumanoidModel.class};
			Class[] var13 = var12;
			boolean var14 = false;
			Class[] var15 = var13;
			int var16 = var15.length;
			int var17 = 0;
			do{
				if ((var17) < (var16)){
					Class var18 = var15[var17];
					if ((this.getParentModel().getClass()) == (var18)){
						var14 = true;
						break;
					}else{
						var17++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if (!(var14)){
				return;
			}else{
				Race var18_1 = ((Race)(((java.util.Optional)(var11.getRace())).orElse((Object)(null))));
				if ((var4 instanceof Player) && ((var18_1) == (ModRaces.FISHMAN.get()))){
					return;
				}else{
					boolean var19 = var4.hasEffect(MobEffects.INVISIBILITY);
					if (((var18_1) != (null)) && ((var18_1.hasRenderFeatures()) && (!(var19)))){
						RacialRenderFeature var20 = ((RacialRenderFeature)(ModMorphs$Client.RACIAL_RENDERERS.get(var18_1)));
						if ((var18_1.hasSubRaces()) && (var11.getSubRace().isPresent())){
							Race var21 = ((Race)(var11.getSubRace().get()));
							var20 = ((RacialRenderFeature)(ModMorphs$Client.RACIAL_RENDERERS.get(var21)));
						}
						if ((var20) == (null)){
							return;
						}else{
							HumanoidModel var21_1 = var20.getModel();
							ResourceLocation var22 = var20.getTexture();
							RacialRenderFeature$ILayerRenderExtension var23 = var20.getExtension();
							if ((((((var23) != (null)) && (var23.render(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10,this.parentRenderer))) ? (1) : (0)) == (0)) && (((var21_1) != (null)) && ((var22) != (null)))){
								int var24 = -1;
								if (var11.getSkinTint().isPresent()){
									var24 = ((Integer)(var11.getSkinTint().get())).intValue();
								}
								this.getParentModel().copyPropertiesTo((EntityModel)(var21_1));
								EntityModel var25 = this.getParentModel();
								if (var25 instanceof HumanoidModel){
									((HumanoidModel)(var25)).copyPropertiesTo(var21_1);
								}
								var21_1.prepareMobModel(var4,var5,var6,var7);
								var21_1.setupAnim(var4,var5,var6,var8,var9,var10);
								var21_1.renderToBuffer(var1,var2.getBuffer(RenderType.entityTranslucentCull(var22)),var3,LivingEntityRenderer.getOverlayCoords(var4,0F),var24);
							}else{
								return;
							}
						}
					}
					return;
				}
			}
		}
	}
}