package xyz.pixelatedw.mineminenomi.api.quests.components;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

@FunctionalInterface()
public interface KillTriggerQuestComponent$IKillEntityEvent {
	public abstract void checkKill(Player var0, LivingEntity var1, DamageSource var2, Objective var3);
}