package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.IConfigEnum;

public enum ServerConfig$OneFruitPerWorldLogic implements IConfigEnum {
	// Fields
	NONE,
	SIMPLE,
	EXTENDED;

	ServerConfig$OneFruitPerWorldLogic() {
	}
	public <T extends IConfigEnum> T next() {
		return (T) (values()[((this.ordinal()) + (1)) % (values().length)]);
	}
}