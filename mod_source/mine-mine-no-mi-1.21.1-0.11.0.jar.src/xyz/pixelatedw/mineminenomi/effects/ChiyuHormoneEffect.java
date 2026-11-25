package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class ChiyuHormoneEffect extends BaseEffect {
	public ChiyuHormoneEffect() {
		super(MobEffectCategory.BENEFICIAL,WyHelper.hexToRGB("#cf90b9").getRGB());
		this.addAttributeModifier(ModAttributes.REGEN_RATE,AttributeHelper.EFFECT_REGEN_RATE_ID,2D,AttributeModifier.Operation.ADD_VALUE);
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return ((var1) % (10)) == (0);
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if ((var1.getHealth()) < (var1.getMaxHealth())){
			var1.heal(1F);
		}
		return true;
	}
	public void stopEffect(LivingEntity var1) {
		var1.addEffect(new MobEffectInstance(MobEffects.HUNGER,200,1));
	}
	public boolean isLingering() {
		return true;
	}
}