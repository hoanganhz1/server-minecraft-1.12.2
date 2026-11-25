package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.PartEntity;

public interface IPartType {
	public abstract PartEntityType<? extends PartEntity<? extends Entity>, ? extends Entity> getPartType();
}