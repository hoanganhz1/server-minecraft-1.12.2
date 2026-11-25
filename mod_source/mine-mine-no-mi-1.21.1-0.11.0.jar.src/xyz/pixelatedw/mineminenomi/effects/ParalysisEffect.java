package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class ParalysisEffect extends BaseEffect {
	public ParalysisEffect() {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#f6fa5c").getRGB());
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED,AttributeHelper.EFFECT_SWIM_SPEED_ID,-0.1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-1D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-0.1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if ((var2) > (0)){
			var1.setDeltaMovement(0D,0D,0D);
		}
		return true;
	}
	public boolean isBlockingSwings() {
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isLingering() {
		return true;
	}
}