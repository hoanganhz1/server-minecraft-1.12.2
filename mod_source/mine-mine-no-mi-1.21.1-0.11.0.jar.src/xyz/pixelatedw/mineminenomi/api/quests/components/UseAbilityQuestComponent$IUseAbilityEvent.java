package xyz.pixelatedw.mineminenomi.api.quests.components;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

@FunctionalInterface()
public interface UseAbilityQuestComponent$IUseAbilityEvent {
	public abstract void useAbility(Player var0, IAbility var1, Objective var2);
}