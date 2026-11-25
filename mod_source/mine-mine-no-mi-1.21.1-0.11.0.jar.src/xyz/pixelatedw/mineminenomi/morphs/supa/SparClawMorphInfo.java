package xyz.pixelatedw.mineminenomi.morphs.supa;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class SparClawMorphInfo extends MorphInfo {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/spar_claw.png");

	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
	}
	@Nullable()
	public @Nullable() ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	public Component getDisplayName() {
		return ((AbilityCore)(SparClawAbility.INSTANCE.get())).getLocalizedName();
	}
	public boolean isPartial() {
		return true;
	}
}