package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.IConfigEnum;

public enum ServerConfig$KeepStatsLogic implements IConfigEnum {
	// Fields
	NONE,
	AUTO,
	FULL,
	CUSTOM;

	ServerConfig$KeepStatsLogic() {
	}
	public <T extends IConfigEnum> T next() {
		return (T) (values()[((this.ordinal()) + (1)) % (values().length)]);
	}
}