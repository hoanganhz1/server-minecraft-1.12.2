package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WashedEffect extends BaseEffect {
	public WashedEffect() {
		super(MobEffectCategory.NEUTRAL,0);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.5D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-0.5D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-4D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE,AttributeHelper.EFFECT_ATTACK_DAMAGE_ID,-6D,AttributeModifier.Operation.ADD_VALUE);
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if (var1.level().isClientSide){
			return false;
		}else{
			MobEffectInstance var3 = var1.getEffect((Holder)(ModEffects.WASHED));
			if ((var3) == (null)){
				return false;
			}else{
				if (((var3.getDuration()) % (10)) == (0)){
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.WASHED.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				}
				if (var1.isInWaterOrRain()){
					var1.removeEffect((Holder)(ModEffects.WASHED));
				}
				return true;
			}
		}
	}
	public boolean isRemoveable() {
		return false;
	}
}