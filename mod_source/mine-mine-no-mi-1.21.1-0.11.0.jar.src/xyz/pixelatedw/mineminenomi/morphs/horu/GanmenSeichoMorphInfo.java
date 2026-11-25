package xyz.pixelatedw.mineminenomi.morphs.horu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.horu.GanmenSeichoHormoneAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GanmenSeichoMorphInfo extends MorphInfo {
	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		EntityModel var5 = var2.getModel();
		if (var5 instanceof HumanoidModel){
			HumanoidModel var6 = ((HumanoidModel)(var5));
			var6.head.xScale = 4F;
			var6.head.yScale = 4F;
			var6.head.zScale = 4F;
			var6.hat.xScale = 4F;
			var6.hat.yScale = 4F;
			var6.hat.zScale = 4F;
		}
	}
	public ResourceLocation getTexture(LivingEntity var1) {
		if (var1 instanceof AbstractClientPlayer){
			return ((AbstractClientPlayer)(var1)).getSkin().texture();
		}else{
			return null;
		}
	}
	public Component getDisplayName() {
		return ((AbilityCore)(GanmenSeichoHormoneAbility.INSTANCE.get())).getLocalizedName();
	}
	public boolean isPartial() {
		return true;
	}
}