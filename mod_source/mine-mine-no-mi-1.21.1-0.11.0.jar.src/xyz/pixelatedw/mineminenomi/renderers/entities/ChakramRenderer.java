package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.ChakramEntity;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ChakramRenderer<T extends ChakramEntity> extends EntityRenderer<T> {
	protected ChakramRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0.05F;
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if ((var1.tickCount) < (2)){
			return;
		}else{
			var4.pushPose();
			float var7 = (((float)(var1.tickCount)) * (20F)) % (360F);
			var4.mulPose(Axis.YP.rotationDegrees((Mth.lerp(var3,var1.yRotO,var1.getYRot())) + (var7)));
			var4.mulPose(Axis.XP.rotationDegrees((Mth.lerp(var3,var1.xRotO,var1.getXRot())) - (180F)));
			var4.mulPose(Axis.ZP.rotationDegrees(90F));
			var4.translate(-0.1D,0.1D,0D);
			LivingEntity var8 = ((LivingEntity)(var1.getOwner()));
			Minecraft.getInstance().getItemRenderer().renderStatic(var8,ModWeapons.CHAKRAM.toStack(),ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,false,var4,var5,var1.level(),var6,OverlayTexture.NO_OVERLAY,(var1.getId()) + (ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal()));
			var4.popPose();
			return;
		}
	}
	public ResourceLocation getTextureLocation(T var1) {
		return null;
	}
}