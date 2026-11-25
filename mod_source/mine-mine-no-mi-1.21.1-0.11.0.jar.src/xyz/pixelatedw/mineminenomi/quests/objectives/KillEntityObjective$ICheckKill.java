package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface KillEntityObjective$ICheckKill {
	public abstract boolean test(Player var0, LivingEntity var1, DamageSource var2);
	public default KillEntityObjective$ICheckKill and(KillEntityObjective$ICheckKill var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default KillEntityObjective$ICheckKill or(KillEntityObjective$ICheckKill var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default KillEntityObjective$ICheckKill negate() { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}