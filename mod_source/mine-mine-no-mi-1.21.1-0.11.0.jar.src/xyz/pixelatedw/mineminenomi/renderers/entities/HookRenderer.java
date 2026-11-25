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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.projectiles.HookEntity;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class HookRenderer<T extends HookEntity> extends EntityRenderer<T> {
	// Fields
	private final ItemStack stack;

	protected HookRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0.05F;
		this.stack = ((ModSwordItem)(ModWeapons.HOOK.get())).getDefaultInstance();
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if ((var1.tickCount) < (2)){
			return;
		}else{
			var4.pushPose();
			var4.mulPose(Axis.YP.rotationDegrees((Mth.lerp(var3,var1.yRotO,var1.getYRot())) + (180F)));
			if (((this.stack) != (null)) && (!(this.stack.isEmpty()))){
				Minecraft.getInstance().getItemRenderer().renderStatic(var1.getOwner(),this.stack,ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,false,var4,var5,var1.level(),var6,OverlayTexture.NO_OVERLAY,(var1.getId()) + (ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal()));
			}
			var4.popPose();
			return;
		}
	}
	public ResourceLocation getTextureLocation(T var1) {
		return null;
	}
}