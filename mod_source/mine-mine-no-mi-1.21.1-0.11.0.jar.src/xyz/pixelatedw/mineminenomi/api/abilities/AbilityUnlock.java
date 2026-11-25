package xyz.pixelatedw.mineminenomi.api.abilities;

import net.neoforged.fml.common.asm.enumextension.IExtensibleEnum;

public enum AbilityUnlock implements IExtensibleEnum {
	// Fields
	NONE,
	PROGRESSION,
	COMMAND;

	private AbilityUnlock() {
	}
	public static AbilityUnlock create(String var0) {
		throw new IllegalStateException((String)("Enum not extended"));
	}
}