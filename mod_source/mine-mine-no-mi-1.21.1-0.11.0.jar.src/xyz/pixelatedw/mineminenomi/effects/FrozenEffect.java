package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBreakableEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IWeakenedByHaoshokuEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class FrozenEffect extends DamageOverTimeEffect implements ITextureOverlayEffect, IBreakableEffect, IBindHandsEffect, IBindBodyEffect, IWeakenedByHaoshokuEffect {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft","textures/block/blue_ice.png");
	private static final Color OVERLAY_COLOR = new Color(1F,1F,1F,0.8F);

	public FrozenEffect() {
		super((l0) -> {
			return ModDamageSources.getInstance((LevelAccessor)(l0.level())).frozen();
		},10F,40);
		super.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-1000D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(NeoForgeMod.SWIM_SPEED,AttributeHelper.EFFECT_SWIM_SPEED_ID,-256D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-4D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,AttributeHelper.EFFECT_KNOCKBACK_RESISTANCE_ID,100D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(ModAttributes.TOUGHNESS,AttributeHelper.EFFECT_TOUGHNESS_ID,2D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(ModAttributes.REGEN_RATE,AttributeHelper.EFFECT_REGEN_RATE_ID,-4D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-256D,AttributeModifier.Operation.ADD_VALUE);
		FrozenEffect var1 = this;
		Function<Integer, Float> var2 = (l0) -> {
			return Float.valueOf(this.getBaseDamage());
		};
		super.setDamageScaling(var2);
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean isBlockingRotation() {
		return true;
	}
	public ResourceLocation getBodyTexture(int var1, int var2) {
		return TEXTURE;
	}
	public Color getTextureOverlayColor() {
		return OVERLAY_COLOR;
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isBlockingSwings() {
		return true;
	}
	public boolean isWeakenedByHaoshoku() {
		return true;
	}
}