package xyz.pixelatedw.mineminenomi.api.effects;

import net.minecraft.world.entity.LivingEntity;

public interface IVanishEffect {
	public abstract boolean isVanished(LivingEntity var0, int var1, int var2);
	public abstract boolean disableParticles();
}