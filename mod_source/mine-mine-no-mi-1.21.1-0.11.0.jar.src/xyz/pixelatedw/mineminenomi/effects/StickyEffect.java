package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class StickyEffect extends BaseEffect implements IColorOverlayEffect, IBindBodyEffect {
	// Fields
	private static final Color OVERLAY_COLOR = new Color(0.62F,0.78F,0F,0.65F);

	public StickyEffect() {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#58db54").getRGB());
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,AttributeHelper.EFFECT_KNOCKBACK_RESISTANCE_ID,1D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.2D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-255D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-4D,AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE,AttributeHelper.EFFECT_ATTACK_DAMAGE_ID,-6D,AttributeModifier.Operation.ADD_VALUE);
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if ((var1.isOnFire()) && ((var1.tickCount) > (0))){
			var1.clearFire();
			AbilityExplosion var3 = new AbilityExplosion((Entity)(var1),(IAbility)(null),var1.getX(),var1.getY(),var1.getZ(),6F);
			var3.setExplosionSound(true);
			var3.setDamageOwner(true);
			var3.setDestroyBlocks(true);
			var3.setFireAfterExplosion(true);
			var3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION6));
			var3.setDamageEntities(true);
			var3.setStaticDamage(100F);
			var3.explode();
			var1.removeEffect((Holder)(ModEffects.STICKY));
		}
		return true;
	}
	public Color getBodyOverlayColor(int var1, int var2) {
		return OVERLAY_COLOR;
	}
	public boolean isBlockingRotation() {
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isLingering() {
		return true;
	}
}