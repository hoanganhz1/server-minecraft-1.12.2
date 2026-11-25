package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;

@FunctionalInterface()
public interface AbilityCore$INodeFactory {
	public abstract AbilityNode create(LivingEntity var0);
}