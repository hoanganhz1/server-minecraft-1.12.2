package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface AbilityDescriptionLine$IDescriptionLine {
	public abstract Component expand(LivingEntity var0, IAbility var1);
	public static AbilityDescriptionLine$IDescriptionLine of(Component var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}