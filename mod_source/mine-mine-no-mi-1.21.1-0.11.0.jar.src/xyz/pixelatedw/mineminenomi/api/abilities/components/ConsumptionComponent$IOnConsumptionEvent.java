package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface ConsumptionComponent$IOnConsumptionEvent {
	public abstract boolean consume(LivingEntity var0, IAbility var1, int var2, float var3);
}