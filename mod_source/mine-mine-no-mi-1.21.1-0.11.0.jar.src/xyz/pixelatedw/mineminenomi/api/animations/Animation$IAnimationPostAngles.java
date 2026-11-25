package xyz.pixelatedw.mineminenomi.api.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface Animation$IAnimationPostAngles<E extends LivingEntity, M extends EntityModel<E>> {
	public abstract void setAnimationPostAngles(E var0, M var1, PoseStack var2, MultiBufferSource var3, int var4, float var5, float var6, float var7, float var8, float var9, float var10);
}