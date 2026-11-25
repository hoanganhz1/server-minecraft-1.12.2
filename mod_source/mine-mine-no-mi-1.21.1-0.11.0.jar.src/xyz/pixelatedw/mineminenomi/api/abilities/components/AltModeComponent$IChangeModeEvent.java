package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface AltModeComponent$IChangeModeEvent<E extends Enum<E>> {
	public abstract boolean changeMode(LivingEntity var0, IAbility var1, E var2);
}