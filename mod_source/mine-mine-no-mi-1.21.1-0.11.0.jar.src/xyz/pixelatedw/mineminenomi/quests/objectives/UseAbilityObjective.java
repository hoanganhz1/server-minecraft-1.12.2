package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IUseAbilityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class UseAbilityObjective extends Objective implements IUseAbilityObjective {
	// Fields
	protected UseAbilityObjective$ICheckAbilityUse useEvent;

	public UseAbilityObjective(Quest var1, Component var2, int var3) {
		this((Quest)(var1),(Component)(var2),var3,((UseAbilityObjective$ICheckAbilityUse)(null)));
	}
	public UseAbilityObjective(Quest var1, Component var2, int var3, AbilityCore<?> var4) {
		this((Quest)(var1),(Component)(var2),var3,(l0, l1) -> {
			return l1.getCore().equals(var4);
		});
	}
	public UseAbilityObjective(Quest var1, Component var2, int var3, UseAbilityObjective$ICheckAbilityUse var4) {
		super(var1,var2);
		UseAbilityObjective$ICheckAbilityUse var5 = (l0, l1) -> {
			return true;
		};
		this.useEvent = var5;
		this.setMaxProgress((float)(var3));
		if ((var4) != (null)){
			this.useEvent = var4;
		}
	}
	public boolean checkAbility(Player var1, IAbility var2) {
		return this.useEvent.test(var1,var2);
	}
}