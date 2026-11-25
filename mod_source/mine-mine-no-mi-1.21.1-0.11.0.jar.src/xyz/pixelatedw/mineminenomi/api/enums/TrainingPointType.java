package xyz.pixelatedw.mineminenomi.api.enums;

import net.neoforged.fml.common.asm.enumextension.IExtensibleEnum;

public enum TrainingPointType implements IExtensibleEnum {
	// Fields
	ACCURACY,
	MARTIAL_ARTS,
	TECHNOLOGY,
	WEAPON_MASTERY;

	private TrainingPointType() {
	}
	public static TrainingPointType create(String var0) {
		throw new IllegalStateException((String)("Enum not extended"));
	}
}