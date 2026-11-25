package xyz.pixelatedw.mineminenomi.api.quests.components;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

@FunctionalInterface()
public interface HitTriggerQuestComponent$IHitEntityEvent {
	public abstract void onHit(Player var0, LivingEntity var1, DamageSource var2, float var3, Objective var4);
}