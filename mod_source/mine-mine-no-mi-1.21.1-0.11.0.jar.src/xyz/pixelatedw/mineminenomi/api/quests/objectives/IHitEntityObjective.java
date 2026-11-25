package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IHitEntityObjective {
	public abstract boolean checkHit(Player var0, LivingEntity var1, DamageSource var2, float var3);
}