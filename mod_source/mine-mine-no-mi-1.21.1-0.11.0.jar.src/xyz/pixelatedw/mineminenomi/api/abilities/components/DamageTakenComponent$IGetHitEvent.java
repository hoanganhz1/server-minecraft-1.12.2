package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface DamageTakenComponent$IGetHitEvent {
	public abstract float damageCheck(LivingEntity var0, IAbility var1, DamageSource var2, float var3);
}