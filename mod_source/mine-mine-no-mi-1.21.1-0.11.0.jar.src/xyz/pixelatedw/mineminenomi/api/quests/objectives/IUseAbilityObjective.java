package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public interface IUseAbilityObjective {
	public abstract boolean checkAbility(Player var0, IAbility var1);
}