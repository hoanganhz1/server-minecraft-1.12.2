package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.LevelEntityGetter;

public interface IEntityGetter {
	public abstract LevelEntityGetter<Entity> mineminenomi$getEntities();
}