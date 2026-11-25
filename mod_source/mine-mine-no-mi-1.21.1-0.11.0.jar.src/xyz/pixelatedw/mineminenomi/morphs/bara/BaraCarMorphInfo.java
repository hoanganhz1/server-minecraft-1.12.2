package xyz.pixelatedw.mineminenomi.morphs.bara;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraCarAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class BaraCarMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(1.3F,0.7F);
	public static final ResourceLocation BARA_CAR_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/bara_car_wheels.png");

	@OnlyIn(value=Dist.CLIENT)
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		var3.translate(0D,0.2D,0D);
	}
	public Component getDisplayName() {
		return ((AbilityCore)(BaraBaraCarAbility.INSTANCE.get())).getLocalizedName();
	}
	@Nullable()
	public @Nullable() ResourceLocation getTexture(LivingEntity var1) {
		return BARA_CAR_TEXTURE;
	}
	public float getEyeHeight(LivingEntity var1) {
		return 0.7F;
	}
	public float getShadowSize() {
		return 1.1F;
	}
	public boolean canMount() {
		return false;
	}
	public boolean isPartial() {
		return true;
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).build();
	}
}