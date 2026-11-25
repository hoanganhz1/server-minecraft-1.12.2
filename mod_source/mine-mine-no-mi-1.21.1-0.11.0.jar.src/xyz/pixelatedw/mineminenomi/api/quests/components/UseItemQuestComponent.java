package xyz.pixelatedw.mineminenomi.api.quests.components;

import java.util.function.Consumer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public class UseItemQuestComponent extends QuestComponent {
	// Fields
	private final PriorityEventPool<UseItemQuestComponent$IUseItemEvent> useItemEvents;

	public UseItemQuestComponent(Objective var1) {
		super(var1);
		this.useItemEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public UseItemQuestComponent addUseEvent(UseItemQuestComponent$IUseItemEvent var1) {
		return this.addUseEvent(100,var1);
	}
	public UseItemQuestComponent addUseEvent(int var1, UseItemQuestComponent$IUseItemEvent var2) {
		this.useItemEvents.addEvent(var1,var2);
		return this;
	}
	public void useItem(Player var1, ItemStack var2, int var3) {
		if (!(super.isValid())){
			return;
		}else{
			UseItemQuestComponent var4 = this;
			Player var5 = var1;
			ItemStack var6 = var2;
			int var7 = var3;
			final Player var5_f1 = var5;
			final ItemStack var6_f2 = var6;
			final int var7_f3 = var7;
			Consumer<UseItemQuestComponent$IUseItemEvent> var8 = (l0) -> {
			l0.useItem(var5_f1,var6_f2,var7_f3,super.getObjective());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.useItemEvents)).dispatch((Consumer)(var8));
			return;
		}
	}
}