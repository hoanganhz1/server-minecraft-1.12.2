package xyz.pixelatedw.mineminenomi.integrations.curios.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.init.ModArmors$Client;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class ArmorCurioRenderer implements ICurioRenderer {
	public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack var1, SlotContext var2, PoseStack var3, RenderLayerParent<T, M> var4, MultiBufferSource var5, int var6, float var7, float var8, float var9, float var10, float var11, float var12) {
		Item var13 = var1.getItem();
		if (var13 instanceof ArmorItem){
			ArmorItem var14 = ((ArmorItem)(var13));
			EquipmentSlot var15 = var2.entity().getEquipmentSlotForItem(var1);
			HumanoidModel var16 = ((HumanoidModel)(ModArmors$Client.ARMOR_MODELS.get(var1.getItem())));
			if ((var16) == (null)){
				LivingEntity var17 = var2.entity();
				if (var17 instanceof AbstractClientPlayer){
					HumanoidArmorModel var18 = new HumanoidArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(((((((AbstractClientPlayer)(var17)).getSkin().model()) == (PlayerSkin.Model.SLIM)) ? (1) : (0)) != (0)) ? (ModelLayers.PLAYER_SLIM_INNER_ARMOR) : (ModelLayers.PLAYER_INNER_ARMOR)));
					var16 = ((HumanoidModel)(ClientHooks.getArmorModel(var2.entity(),var1,var15,(HumanoidModel)(var18))));
				}
			}
			if ((var16) == (null)){
				return;
			}else{
				var16.setupAnim(var2.entity(),var7,var8,var9,var11,var12);
				var16.prepareMobModel(var2.entity(),var7,var8,var9);
				RendererHelper.setPartVisibility(var16,var15);
				HumanoidModel[] var17_1 = new HumanoidModel[]{var16};
				ICurioRenderer.followBodyRotations(var2.entity(),var17_1);
				ArmorMaterial var18_1 = ((ArmorMaterial)(var14.getMaterial().value()));
				if (var1.has(ModDataComponents.MULTI_CHANNEL_DYES)){
					MultiChannelItemColor var19 = ((MultiChannelItemColor)(var1.get(ModDataComponents.MULTI_CHANNEL_DYES)));
					int var20 = 0;
					do{
						if ((var20) < (var18_1.layers().size())){
							ArmorMaterial.Layer var21 = ((ArmorMaterial.Layer)(var18_1.layers().get(var20)));
							if (var21.dyeable()){
								int var22 = var19.getLayerColor((var20) - (1));
								this.renderLayer(var1,var16,var3,var5,var2.entity(),var22,var6,var21);
							}else{
								this.renderLayer(var1,var16,var3,var5,var2.entity(),-1,var6,var21);
							}
							var20++;
							continue;
						}else{
							break;
						}
					} while (true);
				}else{
					if (var1.has(DataComponents.DYED_COLOR)){
						return;
					}else{
						ArmorMaterial.Layer var19_1 = ((ArmorMaterial.Layer)(var18_1.layers().get(0)));
						this.renderLayer(var1,var16,var3,var5,var2.entity(),-1,var6,var19_1);
					}
				}
				return;
			}
		}else{
			return;
		}
	}
	private void renderLayer(ItemStack var1, HumanoidModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, LivingEntity var5, int var6, int var7, ArmorMaterial.Layer var8) {
		boolean var9 = var1.hasFoil();
		var2.renderToBuffer(var3,ItemRenderer.getArmorFoilBuffer(var4,var2.renderType(var1.getItem().getArmorTexture(var1,(Entity)(var5),var1.getEquipmentSlot(),var8,false)),false),var7,OverlayTexture.NO_OVERLAY,var6);
	}
}