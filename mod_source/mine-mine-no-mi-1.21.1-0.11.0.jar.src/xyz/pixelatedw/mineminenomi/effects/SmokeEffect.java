package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByTouchEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SmokeEffect extends BaseEffect implements ITransmisibleByTouchEffect {
	public SmokeEffect() {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#929281").getRGB());
		this.addAttributeModifier(ModAttributes.REGEN_RATE,AttributeHelper.EFFECT_REGEN_RATE_ID,-0.25D,AttributeModifier.Operation.ADD_VALUE);
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return ((var1) % (5)) == (0);
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if (var1.level().isClientSide){
			return false;
		}else{
			MobEffectInstance var3 = var1.getEffect((Holder)(ModEffects.SMOKE));
			if ((var3) == (null)){
				return false;
			}else{
				if (((var3.getDuration()) % (10)) == (0)){
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.SMOKE_DEBUFF.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				}
				return true;
			}
		}
	}
	public boolean isTransmisibleByTouch() {
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isLingering() {
		return true;
	}
}