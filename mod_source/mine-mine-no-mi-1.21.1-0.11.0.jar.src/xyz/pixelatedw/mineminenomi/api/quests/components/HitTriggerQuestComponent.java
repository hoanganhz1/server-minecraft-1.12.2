package xyz.pixelatedw.mineminenomi.api.quests.components;

import java.util.function.Consumer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public class HitTriggerQuestComponent extends QuestComponent {
	// Fields
	private final PriorityEventPool<HitTriggerQuestComponent$IHitEntityEvent> onHitEntityEvents;

	public HitTriggerQuestComponent(Objective var1) {
		super(var1);
		this.onHitEntityEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public HitTriggerQuestComponent addOnHitEvent(HitTriggerQuestComponent$IHitEntityEvent var1) {
		return this.addOnHitEvent(100,var1);
	}
	public HitTriggerQuestComponent addOnHitEvent(int var1, HitTriggerQuestComponent$IHitEntityEvent var2) {
		this.onHitEntityEvents.addEvent(var1,var2);
		return this;
	}
	public void onHit(Player var1, LivingEntity var2, DamageSource var3, float var4) {
		if (!(super.isValid())){
			return;
		}else{
			HitTriggerQuestComponent var5 = this;
			Player var6 = var1;
			LivingEntity var7 = var2;
			DamageSource var8 = var3;
			float var9 = var4;
			final Player var6_f1 = var6;
			final LivingEntity var7_f2 = var7;
			final DamageSource var8_f3 = var8;
			final float var9_f4 = var9;
			Consumer<HitTriggerQuestComponent$IHitEntityEvent> var10 = (l0) -> {
			l0.onHit(var6_f1,var7_f2,var8_f3,var9_f4,super.getObjective());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onHitEntityEvents)).dispatch((Consumer)(var10));
			return;
		}
	}
}