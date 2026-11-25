package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IHealEntityObjective {
	public abstract boolean checkHeal(Player var0, LivingEntity var1, float var2);
}