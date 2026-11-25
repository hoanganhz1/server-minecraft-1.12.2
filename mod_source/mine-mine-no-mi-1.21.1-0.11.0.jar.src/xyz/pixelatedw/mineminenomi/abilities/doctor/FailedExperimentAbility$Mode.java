package xyz.pixelatedw.mineminenomi.abilities.doctor;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public enum FailedExperimentAbility$Mode {
	// Fields
	CONFUSION(MobEffects.CONFUSION),
	ATTACK_SPEED(MobEffects.DIG_SLOWDOWN),
	POISON(MobEffects.POISON),
	HUNGER(MobEffects.HUNGER),
	SLOWNESS(MobEffects.MOVEMENT_SLOWDOWN),
	BLINDNESS(MobEffects.BLINDNESS);
	 Holder<MobEffect> effect;

	private FailedExperimentAbility$Mode(Holder<MobEffect> var3) {
		this.effect = var3;
	}
	public Holder<MobEffect> getEffect() {
		return this.effect;
	}
}