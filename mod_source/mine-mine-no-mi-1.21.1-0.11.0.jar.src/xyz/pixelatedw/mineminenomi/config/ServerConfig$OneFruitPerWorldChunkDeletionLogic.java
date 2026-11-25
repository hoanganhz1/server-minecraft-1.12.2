package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.IConfigEnum;

public enum ServerConfig$OneFruitPerWorldChunkDeletionLogic implements IConfigEnum {
	// Fields
	NONE,
	INSTANT,
	REALISTIC;

	ServerConfig$OneFruitPerWorldChunkDeletionLogic() {
	}
	public <T extends IConfigEnum> T next() {
		return (T) (values()[((this.ordinal()) + (1)) % (values().length)]);
	}
}