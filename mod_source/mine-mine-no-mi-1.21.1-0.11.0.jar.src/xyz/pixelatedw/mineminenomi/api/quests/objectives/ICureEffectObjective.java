package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public interface ICureEffectObjective {
	public abstract boolean checkEffect(Player var0, MobEffectInstance var1);
}