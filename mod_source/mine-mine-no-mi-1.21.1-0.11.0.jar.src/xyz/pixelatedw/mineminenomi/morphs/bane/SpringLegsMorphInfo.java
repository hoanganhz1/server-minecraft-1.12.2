package xyz.pixelatedw.mineminenomi.morphs.bane;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.bane.SpringHopperAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class SpringLegsMorphInfo extends MorphInfo {
	// Fields
	private static final ResourceLocation SPRING_LEGS_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/spring_legs.png");

	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		EntityModel var5 = var2.getModel();
		if (var5 instanceof HumanoidModel){
			HumanoidModel var6 = ((HumanoidModel)(var5));
			var6.rightLeg.visible = false;
			var6.leftLeg.visible = false;
		}
	}
	@Nullable()
	public @Nullable() ResourceLocation getTexture(LivingEntity var1) {
		return SPRING_LEGS_TEXTURE;
	}
	public Component getDisplayName() {
		return ((AbilityCore)(SpringHopperAbility.INSTANCE.get())).getLocalizedName();
	}
	public boolean isPartial() {
		return true;
	}
}