package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface StackComponent$IStacksChangeEvent {
	public abstract void onStacksChange(LivingEntity var0, IAbility var1, int var2);
}