package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface UseAbilityObjective$ICheckAbilityUse {
	public abstract boolean test(Player var0, IAbility var1);
	public default UseAbilityObjective$ICheckAbilityUse and(UseAbilityObjective$ICheckAbilityUse var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default UseAbilityObjective$ICheckAbilityUse or(UseAbilityObjective$ICheckAbilityUse var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}