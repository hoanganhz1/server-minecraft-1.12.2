package xyz.pixelatedw.mineminenomi.morphs.karu;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.karu.IngaZarashiAbility;
import xyz.pixelatedw.mineminenomi.abilities.karu.KarmaAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class IngaZarashiMorphInfo extends MorphInfo {
	// Fields
	private float karma;

	public IngaZarashiMorphInfo() {
		this.karma = 0F;
	}
	@Nullable()
	public @Nullable() ResourceLocation getTexture(LivingEntity var1) {
		if (var1 instanceof AbstractClientPlayer){
			return ((AbstractClientPlayer)(var1)).getSkin().texture();
		}else{
			return null;
		}
	}
	public void preRenderCallback(LivingEntity var1, LivingEntityRenderer<?, ?> var2, PoseStack var3, float var4) {
		Function<AbilityData, KarmaAbility> var5 = (l0) -> {
			return ((KarmaAbility)(l0.getEquippedOrPassiveAbility(((AbilityCore)(KarmaAbility.INSTANCE.get())))));
		};
		this.karma = ((Float)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var5)).map((Function)(((Function<KarmaAbility, Float>)(KarmaAbility::getKarma)))).orElse(Float.valueOf(0F)))).floatValue();
		float var6 = (1F) + ((this.karma) / (300F));
		var3.scale(var6,var6,var6);
	}
	public Component getDisplayName() {
		return ((AbilityCore)(IngaZarashiAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		float var2 = (1.95F) + ((this.karma) / (100F));
		if (var1.isCrouching()){
			var2 = (var2) * (0.84F);
		}
		return var2;
	}
	public float getShadowSize() {
		return (0.5F) + ((this.karma) / (100F));
	}
	@OnlyIn(value=Dist.CLIENT)
	public double getCameraHeight(LivingEntity var1) {
		int var2 = ((Minecraft.getInstance().options.getCameraType()) == (CameraType.FIRST_PERSON)) ? (1) : (0);
		int var3 = ((var1.isPassenger()) && (((var1.getVehicle()) != (null)) && (var1.getVehicle().shouldRiderSit()))) ? (1) : (0);
		if (((var2) != (0)) && ((var3) != (0))){
			return 0.5D;
		}else{
			return 0D;
		}
	}
	public Map<Pose, EntityDimensions> getSizes() {
		float var1 = (1F) + ((this.karma) / (50F));
		float var2 = (1F) + ((this.karma) / (100F));
		return ImmutableMap.builder().put(Pose.STANDING,Player.STANDING_DIMENSIONS.scale(var1,var2)).put(Pose.CROUCHING,Player.STANDING_DIMENSIONS.scale(var1,(var2) * (0.84F))).build();
	}
}