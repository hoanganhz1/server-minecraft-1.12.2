package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.world.entity.Mob;

public interface IMobDetails<T extends Mob> {
	public abstract void init(T var0);
}