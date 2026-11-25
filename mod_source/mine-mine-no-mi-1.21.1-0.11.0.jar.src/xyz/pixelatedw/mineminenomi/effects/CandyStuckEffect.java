package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.effects.IBlockOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBreakableEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IWeakenedByHaoshokuEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class CandyStuckEffect extends DamageOverTimeEffect implements IBlockOverlayEffect, IBreakableEffect, IWeakenedByHaoshokuEffect {
	public CandyStuckEffect() {
		super((l0) -> {
			return l0.damageSources().drown();
		},1F,40);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-100D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED,AttributeHelper.EFFECT_SWIM_SPEED_ID,-256D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-4D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,AttributeHelper.EFFECT_KNOCKBACK_RESISTANCE_ID,100D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(ModAttributes.REGEN_RATE,AttributeHelper.EFFECT_REGEN_RATE_ID,-4D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(ModAttributes.TOUGHNESS,AttributeHelper.EFFECT_TOUGHNESS_ID,2D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-256D,AttributeModifier.Operation.ADD_VALUE);
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean isBlockingRotation() {
		return true;
	}
	public Block getBlockOverlay(int var1, int var2) {
		return ((Block)(ModBlocks.CANDY.get()));
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