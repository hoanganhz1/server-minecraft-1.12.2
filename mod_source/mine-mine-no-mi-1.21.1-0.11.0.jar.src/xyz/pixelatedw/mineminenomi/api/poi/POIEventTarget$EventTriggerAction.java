package xyz.pixelatedw.mineminenomi.api.poi;

import net.minecraft.server.level.ServerLevel;

@FunctionalInterface()
public interface POIEventTarget$EventTriggerAction {
	public abstract void trigger(ServerLevel var0, POIEventTarget var1);
}