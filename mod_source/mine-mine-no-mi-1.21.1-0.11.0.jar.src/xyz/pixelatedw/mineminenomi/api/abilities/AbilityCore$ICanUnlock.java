package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface AbilityCore$ICanUnlock {
	public abstract boolean canUnlock(LivingEntity var0);
	public default AbilityCore$ICanUnlock and(AbilityCore$ICanUnlock var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default AbilityCore$ICanUnlock or(AbilityCore$ICanUnlock var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}