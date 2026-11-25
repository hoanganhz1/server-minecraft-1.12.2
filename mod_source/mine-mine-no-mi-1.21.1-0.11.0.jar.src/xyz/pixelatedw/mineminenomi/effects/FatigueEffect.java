package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class FatigueEffect extends BaseEffect {
	public FatigueEffect() {
		super(MobEffectCategory.HARMFUL,0);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.01D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-0.1D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(Attributes.ATTACK_DAMAGE,AttributeHelper.EFFECT_ATTACK_DAMAGE_ID,-1D,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(ModAttributes.REGEN_RATE,AttributeHelper.EFFECT_REGEN_RATE_ID,-0.1D,AttributeModifier.Operation.ADD_VALUE);
	}
}