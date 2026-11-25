package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.IConfigEnum;

public enum ServerConfig$HaoshokuColoringLogic implements IConfigEnum {
	// Fields
	STANDARD,
	CUSTOM,
	RANDOM;

	ServerConfig$HaoshokuColoringLogic() {
	}
	public <T extends IConfigEnum> T next() {
		return (T) (values()[((this.ordinal()) + (1)) % (values().length)]);
	}
}