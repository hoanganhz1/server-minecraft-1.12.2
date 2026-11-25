package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.PartEntity;

@FunctionalInterface()
public interface PartEntityType$PartEntityFactory<P extends PartEntity<T>, T extends Entity> {
	public abstract P create(T var0);
}