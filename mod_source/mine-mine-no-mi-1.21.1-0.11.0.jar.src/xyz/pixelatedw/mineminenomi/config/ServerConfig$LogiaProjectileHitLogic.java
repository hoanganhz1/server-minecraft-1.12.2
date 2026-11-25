package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.IConfigEnum;

public enum ServerConfig$LogiaProjectileHitLogic implements IConfigEnum {
	// Fields
	NONE,
	HAKI,
	EXTENDED;

	ServerConfig$LogiaProjectileHitLogic() {
	}
	public <T extends IConfigEnum> T next() {
		return (T) (values()[((this.ordinal()) + (1)) % (values().length)]);
	}
}