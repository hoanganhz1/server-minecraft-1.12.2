package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface HitTriggerComponent$IOnHitEvent {
	public abstract boolean onHit(LivingEntity var0, LivingEntity var1, DamageSource var2, IAbility var3);
}