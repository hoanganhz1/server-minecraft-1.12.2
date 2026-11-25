package xyz.pixelatedw.mineminenomi.api.quests.components;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

@FunctionalInterface()
public interface DamageTakenQuestComponent$IGetHitEvent {
	public abstract void damageCheck(Player var0, DamageSource var1, float var2, Objective var3);
}