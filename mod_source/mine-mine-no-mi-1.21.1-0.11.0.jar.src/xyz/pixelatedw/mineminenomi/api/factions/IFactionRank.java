package xyz.pixelatedw.mineminenomi.api.factions;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

public interface IFactionRank {
	public abstract Component getLocalizedName();
	public abstract boolean isUnlocked(LivingEntity var0);
}