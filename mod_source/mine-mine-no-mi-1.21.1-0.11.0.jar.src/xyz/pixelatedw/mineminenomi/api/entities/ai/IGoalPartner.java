package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.entity.LivingEntity;

public interface IGoalPartner<T extends LivingEntity> {
	public abstract T getPartner();
	public abstract void setPartner(T var0);
}