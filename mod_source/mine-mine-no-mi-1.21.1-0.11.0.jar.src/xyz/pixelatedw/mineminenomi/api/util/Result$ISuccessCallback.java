package xyz.pixelatedw.mineminenomi.api.util;

import net.minecraft.network.chat.Component;

@FunctionalInterface()
public interface Result$ISuccessCallback {
	public abstract void callback(Component var0);
}