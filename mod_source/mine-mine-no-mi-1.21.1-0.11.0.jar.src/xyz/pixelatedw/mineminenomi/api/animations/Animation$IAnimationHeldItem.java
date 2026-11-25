package xyz.pixelatedw.mineminenomi.api.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface()
public interface Animation$IAnimationHeldItem<E extends LivingEntity> {
	public abstract void setupHeldItem(E var0, ItemStack var1, ItemDisplayContext var2, HumanoidArm var3, PoseStack var4, MultiBufferSource var5, int var6);
}