package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;

@FunctionalInterface()
public interface StyleSwitchGoal$IStyleSwitchEvent {
	public abstract FightingStyle nextChange(Mob var0, LivingEntity var1, FightingStyle var2);
}