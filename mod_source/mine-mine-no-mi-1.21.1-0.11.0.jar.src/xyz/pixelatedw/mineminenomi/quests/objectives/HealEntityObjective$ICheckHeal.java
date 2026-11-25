package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface HealEntityObjective$ICheckHeal {
	public abstract boolean test(Player var0, LivingEntity var1, float var2);
	public default HealEntityObjective$ICheckHeal and(HealEntityObjective$ICheckHeal var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default HealEntityObjective$ICheckHeal or(HealEntityObjective$ICheckHeal var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}