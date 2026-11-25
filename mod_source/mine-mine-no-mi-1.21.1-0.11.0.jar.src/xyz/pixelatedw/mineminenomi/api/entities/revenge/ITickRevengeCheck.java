package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.entity.LivingEntity;

public interface ITickRevengeCheck extends IRevengeCheck {
	public abstract boolean check(LivingEntity var0);
}