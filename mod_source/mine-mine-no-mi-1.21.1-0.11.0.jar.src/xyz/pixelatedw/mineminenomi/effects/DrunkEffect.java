package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;

public class DrunkEffect extends BaseEffect {
	public DrunkEffect() {
		super(MobEffectCategory.HARMFUL,0);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.005D,AttributeModifier.Operation.ADD_VALUE);
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if (((var2) >= (2)) && ((var2) < (4))){
			var1.addEffect(new MobEffectInstance(MobEffects.CONFUSION,100,0));
		}else{
			if ((var2) >= (4)){
				var1.addEffect(new MobEffectInstance(MobEffects.CONFUSION,100,2));
			}else{
				return true;
			}
		}
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
}