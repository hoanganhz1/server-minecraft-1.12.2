package xyz.pixelatedw.mineminenomi.api.challenges;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@FunctionalInterface()
public interface ChallengeCore$ITargetShowcase {
	public abstract LivingEntity createShowcase(Level var0);
}