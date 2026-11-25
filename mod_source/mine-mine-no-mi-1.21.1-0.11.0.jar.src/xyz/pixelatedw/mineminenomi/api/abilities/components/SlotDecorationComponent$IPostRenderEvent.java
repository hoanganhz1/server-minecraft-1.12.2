package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;

@FunctionalInterface()
public interface SlotDecorationComponent$IPostRenderEvent {
	public abstract void postRender(LivingEntity var0, Minecraft var1, PoseStack var2, MultiBufferSource var3, TexturedRectUI var4, float var5, float var6, float var7);
}