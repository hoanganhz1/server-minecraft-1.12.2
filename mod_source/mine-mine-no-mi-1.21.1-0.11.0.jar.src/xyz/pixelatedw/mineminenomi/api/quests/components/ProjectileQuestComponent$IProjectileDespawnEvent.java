package xyz.pixelatedw.mineminenomi.api.quests.components;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

@FunctionalInterface()
public interface ProjectileQuestComponent$IProjectileDespawnEvent {
	public abstract void projectileDespawn(Player var0, Projectile var1, Objective var2);
}