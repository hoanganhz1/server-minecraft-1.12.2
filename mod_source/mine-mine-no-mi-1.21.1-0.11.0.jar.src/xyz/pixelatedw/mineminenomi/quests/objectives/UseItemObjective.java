package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IUseItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class UseItemObjective extends Objective implements IUseItemObjective {
	// Fields
	protected UseItemObjective$ICheckItemUse useEvent;

	public UseItemObjective(Quest var1, Component var2, int var3) {
		this((Quest)(var1),(Component)(var2),var3,((UseItemObjective$ICheckItemUse)(null)));
	}
	public UseItemObjective(Quest var1, Component var2, int var3, Item var4) {
		this((Quest)(var1),(Component)(var2),var3,(l0, l1, l2) -> {
			return (l1.getItem()) == (var4);
		});
	}
	public UseItemObjective(Quest var1, Component var2, int var3, UseItemObjective$ICheckItemUse var4) {
		super(var1,var2);
		UseItemObjective$ICheckItemUse var5 = (l0, l1, l2) -> {
			return true;
		};
		this.useEvent = var5;
		this.setMaxProgress((float)(var3));
		if ((var4) != (null)){
			this.useEvent = var4;
		}
	}
	public boolean checkItem(Player var1, ItemStack var2, int var3) {
		return this.useEvent.test(var1,var2,var3);
	}
}