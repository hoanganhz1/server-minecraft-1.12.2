package xyz.pixelatedw.mineminenomi.api.util;

@FunctionalInterface()
public interface Result$IFailCallback<T extends Object> {
	public abstract void callback(T var0);
}