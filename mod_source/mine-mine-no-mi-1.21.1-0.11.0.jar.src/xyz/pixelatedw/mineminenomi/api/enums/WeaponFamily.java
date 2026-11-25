package xyz.pixelatedw.mineminenomi.api.enums;

import net.neoforged.fml.common.asm.enumextension.IExtensibleEnum;

public enum WeaponFamily implements IExtensibleEnum {
	// Fields
	UNKNOWN,
	SWORD,
	AXE,
	TRIDENT,
	BOW,
	CROSSBOW,
	BLUNT,
	GUN,
	POLEARM;

	private WeaponFamily() {
	}
	public static WeaponFamily create(String var0) {
		throw new IllegalStateException((String)("Enum not extended"));
	}
}