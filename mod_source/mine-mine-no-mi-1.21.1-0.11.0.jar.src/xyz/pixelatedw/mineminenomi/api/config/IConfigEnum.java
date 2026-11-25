package xyz.pixelatedw.mineminenomi.api.config;

public interface IConfigEnum {
	public abstract <T extends IConfigEnum> T next();
}