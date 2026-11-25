package xyz.pixelatedw.mineminenomi.api.poi;

import net.minecraft.util.RandomSource;

public interface INotoriousTarget {
	public abstract RandomSource getTrackedRandom();
	public abstract TrackedNPC getTrackedData();
}