package xyz.pixelatedw.mineminenomi.integrations.curios.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ItemCurioRenderer implements ICurioRenderer {
	public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack var1, SlotContext var2, PoseStack var3, RenderLayerParent<T, M> var4, MultiBufferSource var5, int var6, float var7, float var8, float var9, float var10, float var11, float var12) {
		var3.pushPose();
		EntityRenderer var13 = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer((Entity)(var2.entity()));
		if (var13 instanceof LivingEntityRenderer){
			EntityModel var14 = ((LivingEntityRenderer)(var13)).getModel();
			if (var14 instanceof HumanoidModel){
				((HumanoidModel)(var14)).getHead().translateAndRotate(var3);
			}
		}
		var3.translate(0D,-0.25D,0D);
		var3.mulPose(Axis.YP.rotationDegrees(180F));
		var3.scale(0.625F,-0.625F,-0.625F);
		Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(var2.entity(),var1,ItemDisplayContext.HEAD,false,var3,var5,var6);
		var3.popPose();
	}
}