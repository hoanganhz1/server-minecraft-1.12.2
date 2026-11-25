package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.IConfigEnum;

public enum ServerConfig$HaoshokuUnlockLogic implements IConfigEnum {
	// Fields
	NONE,
	RANDOM,
	EXPERIENCE,
	COMBINED,
	TRUE_RANDOM;

	ServerConfig$HaoshokuUnlockLogic() {
	}
	public <T extends IConfigEnum> T next() {
		return (T) (values()[((this.ordinal()) + (1)) % (values().length)]);
	}
}