package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface IHitRevengeCheck extends IRevengeCheck {
	public abstract boolean check(LivingEntity var0, DamageSource var1);
}