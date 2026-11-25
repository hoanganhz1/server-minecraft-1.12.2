package xyz.pixelatedw.mineminenomi.quests.objectives;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IHealEntityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class HealEntityObjective extends Objective implements IHealEntityObjective {
	// Fields
	protected HealEntityObjective$ICheckHeal healEvent;

	public HealEntityObjective(Quest var1, Component var2, int var3) {
		this((Quest)(var1),(Component)(var2),var3,((HealEntityObjective$ICheckHeal)(null)));
	}
	public HealEntityObjective(Quest var1, Component var2, int var3, EntityType<?> var4) {
		this((Quest)(var1),(Component)(var2),var3,(l0, l1, l2) -> {
			return (l1.getType()) == (var4);
		});
	}
	public HealEntityObjective(Quest var1, Component var2, int var3, @Nullable() HealEntityObjective$ICheckHeal var4) {
		super(var1,var2);
		HealEntityObjective$ICheckHeal var5 = (l0, l1, l2) -> {
			return true;
		};
		this.healEvent = var5;
		this.setMaxProgress((float)(var3));
		if ((var4) != (null)){
			this.healEvent = var4;
		}
	}
	public boolean checkHeal(Player var1, LivingEntity var2, float var3) {
		return this.healEvent.test(var1,var2,var3);
	}
}