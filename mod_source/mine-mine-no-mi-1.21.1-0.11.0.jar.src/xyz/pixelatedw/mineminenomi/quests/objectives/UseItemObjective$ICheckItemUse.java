package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface()
public interface UseItemObjective$ICheckItemUse {
	public abstract boolean test(Player var0, ItemStack var1, int var2);
	public default UseItemObjective$ICheckItemUse and(UseItemObjective$ICheckItemUse var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public default UseItemObjective$ICheckItemUse or(UseItemObjective$ICheckItemUse var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}