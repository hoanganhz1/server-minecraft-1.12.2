package xyz.pixelatedw.mineminenomi.api.quests.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$DamageState;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public class DamageTakenQuestComponent extends QuestComponent {
	// Fields
	private final PriorityEventPool<DamageTakenQuestComponent$IGetHitEvent> onAttackEvents;
	private final PriorityEventPool<DamageTakenQuestComponent$IGetHitEvent> onHurtEvents;
	private final PriorityEventPool<DamageTakenQuestComponent$IGetHitEvent> onDamageEvents;

	public DamageTakenQuestComponent(Objective var1) {
		super(var1);
		this.onAttackEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onHurtEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onDamageEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public DamageTakenQuestComponent addOnAttackEvent(DamageTakenQuestComponent$IGetHitEvent var1) {
		return this.addOnAttackEvent(100,var1);
	}
	public DamageTakenQuestComponent addOnAttackEvent(int var1, DamageTakenQuestComponent$IGetHitEvent var2) {
		this.onAttackEvents.addEvent(var1,var2);
		return this;
	}
	public DamageTakenQuestComponent addOnHurtEvent(DamageTakenQuestComponent$IGetHitEvent var1) {
		return this.addOnHurtEvent(100,var1);
	}
	public DamageTakenQuestComponent addOnHurtEvent(int var1, DamageTakenQuestComponent$IGetHitEvent var2) {
		this.onHurtEvents.addEvent(var1,var2);
		return this;
	}
	public DamageTakenQuestComponent addOnDamageEvent(DamageTakenQuestComponent$IGetHitEvent var1) {
		return this.addOnDamageEvent(100,var1);
	}
	public DamageTakenQuestComponent addOnDamageEvent(int var1, DamageTakenQuestComponent$IGetHitEvent var2) {
		this.onDamageEvents.addEvent(var1,var2);
		return this;
	}
	public void checkDamageTaken(Player var1, DamageSource var2, float var3, DamageTakenComponent$DamageState var4) {
		if (!(super.isValid())){
			return;
		}else{
			List var5 = new ArrayList();
			switch (DamageTakenQuestComponent$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$DamageTakenComponent$DamageState[var4.ordinal()]){
			case 1:
				var5 = this.onAttackEvents.getEventsStream().toList();
				break;
			case 2:
				var5 = this.onHurtEvents.getEventsStream().toList();
				break;
			case 3:
				var5 = this.onDamageEvents.getEventsStream().toList();
				break;
			}
			Iterator var6 = var5.iterator();
			do{
				if (var6.hasNext()){
					((DamageTakenQuestComponent$IGetHitEvent)(var6.next())).damageCheck(var1,var2,var3,super.getObjective());
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}