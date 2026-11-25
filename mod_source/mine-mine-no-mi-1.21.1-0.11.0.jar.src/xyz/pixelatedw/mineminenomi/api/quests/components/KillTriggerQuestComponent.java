package xyz.pixelatedw.mineminenomi.api.quests.components;

import java.util.function.Consumer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public class KillTriggerQuestComponent extends QuestComponent {
	// Fields
	private final PriorityEventPool<KillTriggerQuestComponent$IKillEntityEvent> onKillEntityEvents;

	public KillTriggerQuestComponent(Objective var1) {
		super(var1);
		this.onKillEntityEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public KillTriggerQuestComponent addOnKillEvent(KillTriggerQuestComponent$IKillEntityEvent var1) {
		return this.addOnKillEvent(100,var1);
	}
	public KillTriggerQuestComponent addOnKillEvent(int var1, KillTriggerQuestComponent$IKillEntityEvent var2) {
		this.onKillEntityEvents.addEvent(var1,var2);
		return this;
	}
	public void checkKill(Player var1, LivingEntity var2, DamageSource var3) {
		if (!(super.isValid())){
			return;
		}else{
			KillTriggerQuestComponent var4 = this;
			Player var5 = var1;
			LivingEntity var6 = var2;
			DamageSource var7 = var3;
			final Player var5_f1 = var5;
			final LivingEntity var6_f2 = var6;
			final DamageSource var7_f3 = var7;
			Consumer<KillTriggerQuestComponent$IKillEntityEvent> var8 = (l0) -> {
			l0.checkKill(var5_f1,var6_f2,var7_f3,super.getObjective());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onKillEntityEvents)).dispatch((Consumer)(var8));
			return;
		}
	}
}