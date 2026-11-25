package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

@FunctionalInterface()
public interface TeleportComponent$ITeleportEvent {
	public abstract void fireTeleportEvent(LivingEntity var0, BlockPos var1, IAbility var2);
}