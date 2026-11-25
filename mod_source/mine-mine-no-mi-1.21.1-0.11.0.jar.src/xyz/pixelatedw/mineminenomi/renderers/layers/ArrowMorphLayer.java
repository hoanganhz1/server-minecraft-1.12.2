package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class ArrowMorphLayer<T extends LivingEntity, M extends EntityModel<T>> extends StuckInMorphBodyLayer<T, M> {
	// Fields
	private final EntityRenderDispatcher dispatcher;

	public ArrowMorphLayer(EntityRendererProvider.Context var1, LivingEntityRenderer<T, M> var2) {
		super(var2);
		this.dispatcher = var1.getEntityRenderDispatcher();
	}
	protected int numStuck(T var1) {
		return var1.getArrowCount();
	}
	protected void renderStuckItem(PoseStack var1, MultiBufferSource var2, int var3, Entity var4, float var5, float var6, float var7, float var8) {
		float var9 = Mth.sqrt(((var5) * (var5)) + ((var7) * (var7)));
		Arrow var10 = new Arrow(var4.level(),var4.getX(),var4.getY(),var4.getZ(),ItemStack.EMPTY,(ItemStack)(null));
		var10.setYRot((float)((Math.atan2((double)(var5),(double)(var7))) * (57.2957763671875D)));
		var10.setXRot((float)((Math.atan2((double)(var6),(double)(var9))) * (57.2957763671875D)));
		var10.yRotO = var10.getYRot();
		var10.xRotO = var10.getXRot();
		this.dispatcher.render((Entity)(var10),0D,0D,0D,0F,var8,var1,var2,var3);
	}
}