package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.IConfigEnum;

public enum ClientConfig$SlotNumberVisuals implements IConfigEnum {
	// Fields
	TICKS,
	SECONDS,
	PERCENTAGE;

	private ClientConfig$SlotNumberVisuals() {
	}
	public <T extends IConfigEnum> T next() {
		return (T) (values()[((this.ordinal()) + (1)) % (values().length)]);
	}
}