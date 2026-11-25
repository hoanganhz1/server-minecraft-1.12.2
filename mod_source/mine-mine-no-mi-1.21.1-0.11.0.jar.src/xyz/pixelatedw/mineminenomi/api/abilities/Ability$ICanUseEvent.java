package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.util.Result;

@FunctionalInterface()
public interface Ability$ICanUseEvent {
	public abstract Result canUse(LivingEntity var0, IAbility var1);
	public default Ability$ICanUseEvent and(Ability$ICanUseEvent var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default Ability$ICanUseEvent or(Ability$ICanUseEvent var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default Ability$ICanUseEvent not() { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}