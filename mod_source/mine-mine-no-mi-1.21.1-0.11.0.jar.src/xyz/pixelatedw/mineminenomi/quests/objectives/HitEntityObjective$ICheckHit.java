package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@FunctionalInterface()
public interface HitEntityObjective$ICheckHit {
	public abstract boolean test(Player var0, LivingEntity var1, DamageSource var2, float var3);
	public default HitEntityObjective$ICheckHit and(HitEntityObjective$ICheckHit var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default HitEntityObjective$ICheckHit or(HitEntityObjective$ICheckHit var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default HitEntityObjective$ICheckHit negate() { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}