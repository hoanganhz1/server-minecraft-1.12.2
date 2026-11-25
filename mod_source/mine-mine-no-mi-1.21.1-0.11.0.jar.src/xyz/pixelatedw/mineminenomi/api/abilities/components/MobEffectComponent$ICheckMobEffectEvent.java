package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public interface MobEffectComponent$ICheckMobEffectEvent {
	public abstract boolean checkPotion(LivingEntity var0, MobEffectInstance var1);
}