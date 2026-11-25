package xyz.pixelatedw.mineminenomi.data.entity;

import net.minecraft.core.Direction8;

public final record HisoLookoutData$HisoLookoutTarget(String username, long time, Direction8 direction) {
	public long passedMinutes(long var1) {
		return (((var1) - (this.time)) / (20L)) / (60L);
	}
}